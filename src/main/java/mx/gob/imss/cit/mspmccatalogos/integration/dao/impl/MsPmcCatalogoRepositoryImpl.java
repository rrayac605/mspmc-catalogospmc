package mx.gob.imss.cit.mspmccatalogos.integration.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import mx.gob.imss.cit.mspmccatalogos.controller.MsPmcCatalogoInput;
import mx.gob.imss.cit.mspmccatalogos.integration.dao.MsPmcCatalogoRepository;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.integration.model.MsPmcCatalogoDTO;
import mx.gob.imss.cit.mspmccommons.utils.CustomAggregationOperation;

@Repository
public class MsPmcCatalogoRepositoryImpl implements MsPmcCatalogoRepository{

	@Autowired
	private MongoOperations mongoOperations;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Object> getCatalog(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException {
		StringBuilder builder= new StringBuilder();
		builder.append("mx.gob.imss.cit.mspmccommons.integration.model.");
		builder.append(input.getNameCatalog());
		builder.append("");
		
		ClassLoader classLoader = this.getClass().getClassLoader();
		Class<?> loadMyClass = classLoader.loadClass(builder.toString());
				
		Query query = new Query();
		query.addCriteria(Criteria.byExample(loadMyClass));
		@SuppressWarnings("unchecked")
		List<Object> catalogoDTO = (List<Object>) mongoOperations.findAll(loadMyClass);
		logger.debug("Obteniendo catalogo: {}", input.getNameCatalog());
		int size = 0;
		if(catalogoDTO.isEmpty()) {
			size = 0;
		} else {
			size = catalogoDTO.size();
		}
		logger.debug("Registros obtenidos: {}", size);
		return catalogoDTO;
	}
	
	@Override
	public List<MsPmcCatalogoDTO> getCatalogRegex(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException {

		if (input.getTextRegex() == null) {
			return new ArrayList<>();
		}

		String classPackage = "mx.gob.imss.cit.mspmccommons.integration.model." + input.getNameCatalog();

		ClassLoader classLoader = this.getClass().getClassLoader();
		Class<?> loadMyClass = classLoader.loadClass(classPackage);

		TypedAggregation<MsPmcCatalogoDTO> aggregation = buildAggregation(input.getTextRegex(), input.getSearchField());
		logger.info("Agregacion: {}", aggregation);
		AggregationResults<MsPmcCatalogoDTO> aggregationResults = mongoOperations.aggregate(aggregation, loadMyClass,
				MsPmcCatalogoDTO.class);
		logger.debug("Obteniendo catalogo Regex: {}", input.getNameCatalog());
		logger.debug("Registros obtenidos Regex: {}", aggregationResults.getMappedResults().size());
		return aggregationResults.getMappedResults();
	}

	private TypedAggregation<MsPmcCatalogoDTO> buildAggregation(String textRegex, String searchField) {
		String addFieldsJson = buildAddFieldsString(searchField, textRegex);
		CustomAggregationOperation addFields = new CustomAggregationOperation(addFieldsJson);
		MatchOperation match = Aggregation.match(Criteria.where("result").is(Boolean.TRUE));
		String groupJson = buildGroupString(searchField);
		CustomAggregationOperation group = new CustomAggregationOperation(groupJson);
		LimitOperation limit = Aggregation.limit(60);
		return Aggregation.newAggregation(MsPmcCatalogoDTO.class, addFields, match, limit, group);
	}

	private String buildAddFieldsString(String searchField, String textRegex) {
		String addFields = "{ $addFields: { result: { $regexMatch: { input: '$";
		addFields = addFields.concat(searchField)
				.concat("', regex: /")
				.concat(textRegex)
				.concat("/i } } } }");
		return addFields;
	}

	private String buildGroupString(String searchField) {
		String searchFieldId = searchField.replace("des", "cveId");
		String group = "{ $group: { _id: '$_id', 'cveCatalogo': { $first: '$";
		group = group.concat(searchFieldId)
				.concat("' }, 'descCatalogo': { $first: '$")
				.concat(searchField)
				.concat("' } } }");
		return group;
	}

}
