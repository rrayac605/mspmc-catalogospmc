package mx.gob.imss.cit.mspmccatalogos.integration.dao;

import java.util.List;

import mx.gob.imss.cit.mspmccatalogos.controller.MsPmcCatalogoInput;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.integration.model.MsPmcCatalogoDTO;

public interface MsPmcCatalogoRepository {

	List<Object> getCatalog(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException;
	List<MsPmcCatalogoDTO> getCatalogRegex(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException;

}
