package mx.gob.imss.cit.mspmccatalogos.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.mspmccatalogos.controller.MsPmcCatalogoInput;
import mx.gob.imss.cit.mspmccatalogos.integration.dao.MsPmcCatalogoRepository;
import mx.gob.imss.cit.mspmccatalogos.services.MsPmcCatalagosService;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.integration.model.MccAccionRegistro;
import mx.gob.imss.cit.mspmccommons.integration.model.MccActoInseguro;
import mx.gob.imss.cit.mspmccommons.integration.model.MccCasoRegistro;
import mx.gob.imss.cit.mspmccommons.integration.model.MccCausaExterna;
import mx.gob.imss.cit.mspmccommons.integration.model.MccClase;
import mx.gob.imss.cit.mspmccommons.integration.model.MccCodigoDiagnostico;
import mx.gob.imss.cit.mspmccommons.integration.model.MccConsecuencia;
import mx.gob.imss.cit.mspmccommons.integration.model.MccError;
import mx.gob.imss.cit.mspmccommons.integration.model.MccEstadoArchivo;
import mx.gob.imss.cit.mspmccommons.integration.model.MccEstadoRegistro;
import mx.gob.imss.cit.mspmccommons.integration.model.MccFraccion;
import mx.gob.imss.cit.mspmccommons.integration.model.MccLaudo;
import mx.gob.imss.cit.mspmccommons.integration.model.MccModificacionPatronal;
import mx.gob.imss.cit.mspmccommons.integration.model.MccNaturaleza;
import mx.gob.imss.cit.mspmccommons.integration.model.MccOcupacionSisat;
import mx.gob.imss.cit.mspmccommons.integration.model.MccRiesgoFisico;
import mx.gob.imss.cit.mspmccommons.integration.model.MccSituacionRegistro;
import mx.gob.imss.cit.mspmccommons.integration.model.MccTipoArchivo;
import mx.gob.imss.cit.mspmccommons.integration.model.MccTipoRegistro;
import mx.gob.imss.cit.mspmccommons.integration.model.MccTipoRiesgo;
import mx.gob.imss.cit.mspmccommons.integration.model.MsPmcCatalogoDTO;

@Service("msPmcCatalagosServiceImpl")
public class MsPmcCatalagosServiceImpl implements MsPmcCatalagosService {

	@Autowired
	MsPmcCatalogoRepository msPmcCatalogoRepository;
	
	@Override
	public List<MsPmcCatalogoDTO> consultarCatalogo(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException {
		
		return obtenerCatalogos(input);
		
	}

	
	@Override
	public List<MsPmcCatalogoDTO> consultarCatalogoRegex(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException {
		return msPmcCatalogoRepository.getCatalogRegex(input);
	}
	
	private List<MsPmcCatalogoDTO> obtenerCatalogos(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException {
		
		List<?> objects = msPmcCatalogoRepository.getCatalog(input);
		List<MsPmcCatalogoDTO> catalogoDTOList = new ArrayList<>();
		
		if (objects != null && !objects.isEmpty()) {
			for (int i = 0; i < objects.size(); i++) {
				catalogos(objects, i, catalogoDTOList);
			} 
		}
		
		return catalogoDTOList;
	}
	
	private void catalogos(List<?> objects, int i, List<MsPmcCatalogoDTO> catalogoDTOList) {
		
		MsPmcCatalogoDTO catalogoDTO = new MsPmcCatalogoDTO();
		
		if (objects.get(i) instanceof MccCasoRegistro) {
			MccCasoRegistro casoRegistro = (MccCasoRegistro) objects.get(i);
			catalogoDTO.setCveCatalogo(casoRegistro.getCveIdCasoRegistro());
			catalogoDTO.setDescCatalogo(casoRegistro.getDesCasoRegistro());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccTipoRegistro) {
			MccTipoRegistro mccTipoRegistro = (MccTipoRegistro) objects.get(i);
			catalogoDTO.setCveCatalogo(mccTipoRegistro.getCveIdTipoRegistro());
			catalogoDTO.setDescCatalogo(mccTipoRegistro.getDesDescripcion());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccAccionRegistro) {
			MccAccionRegistro accionRegistro = (MccAccionRegistro) objects.get(i);
			catalogoDTO.setCveCatalogo(String.valueOf(accionRegistro.getCveIdAccionRegistro()));
			catalogoDTO.setDescCatalogo(accionRegistro.getDesAccionRegistro());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccCausaExterna) {
			MccCausaExterna causaExterna = (MccCausaExterna) objects.get(i);
			catalogoDTO.setCveCatalogo(causaExterna.getCveIdCausaExterna());
			catalogoDTO.setDescCatalogo(causaExterna.getDesCausaExterna());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccCodigoDiagnostico) {
			MccCodigoDiagnostico mccCodigoDiagnostico = (MccCodigoDiagnostico) objects.get(i);
			catalogoDTO.setCveCatalogo(mccCodigoDiagnostico.getCveIdCodigoDiagnostico());
			catalogoDTO.setDescCatalogo(mccCodigoDiagnostico.getDesCodigoDiagnostico());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccConsecuencia) {
			MccConsecuencia mccConsecuencia = (MccConsecuencia) objects.get(i);
			catalogoDTO.setCveCatalogo(mccConsecuencia.getCveIdConsecuencia());
			catalogoDTO.setDescCatalogo(mccConsecuencia.getDesConsecuencia());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccError) {
			MccError mccError = (MccError) objects.get(i);
			catalogoDTO.setCveCatalogo(mccError.getCveIdCodigoError());
			catalogoDTO.setDescCatalogo(mccError.getDesCodigoError());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccEstadoArchivo) {
			MccEstadoArchivo mccEstadoArchivom = (MccEstadoArchivo) objects.get(i);
			catalogoDTO.setCveCatalogo(mccEstadoArchivom.getCveIdEstadoArchivo());
			catalogoDTO.setDescCatalogo(mccEstadoArchivom.getDesEstadoArchivo());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccEstadoRegistro) {
			MccEstadoRegistro mccEstadoRegistro = (MccEstadoRegistro) objects.get(i);
			catalogoDTO.setCveCatalogo(mccEstadoRegistro.getCveIdEstadoRegistro());
			catalogoDTO.setDescCatalogo(mccEstadoRegistro.getDesEstadoRegistro());
			catalogoDTOList.add(catalogoDTO);
		}
		
		if (objects.get(i) instanceof MccLaudo) {
			MccLaudo mccLaudo = (MccLaudo) objects.get(i);
			catalogoDTO.setCveCatalogo(mccLaudo.getCveIdLaudo());
			catalogoDTO.setDescCatalogo(mccLaudo.getDesLaudo());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccNaturaleza) {
			MccNaturaleza mccNaturaleza = (MccNaturaleza) objects.get(i);
			catalogoDTO.setCveCatalogo(mccNaturaleza.getCveIdNaturaleza());
			catalogoDTO.setDescCatalogo(mccNaturaleza.getDesNaturaleza());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccOcupacionSisat) {
			MccOcupacionSisat mccOcupacionSisat = (MccOcupacionSisat) objects.get(i);
			catalogoDTO.setCveCatalogo(mccOcupacionSisat.getCveOcupacion());
			catalogoDTO.setDescCatalogo(mccOcupacionSisat.getDesOcupacion());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccRiesgoFisico) {
			MccRiesgoFisico mccRiesgoFisico = (MccRiesgoFisico) objects.get(i);
			catalogoDTO.setCveCatalogo(mccRiesgoFisico.getCveIdRiesgoFisico());
			catalogoDTO.setDescCatalogo(mccRiesgoFisico.getDesRiesgoFisico());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccSituacionRegistro) {
			MccSituacionRegistro mccSituacionRegistro = (MccSituacionRegistro) objects.get(i);
			catalogoDTO.setCveCatalogo(mccSituacionRegistro.getCveIdSituacionRegistro());
			catalogoDTO.setDescCatalogo(mccSituacionRegistro.getDesSituacionRegistro());
			catalogoDTOList.add(catalogoDTO);
		}
		
		if (objects.get(i) instanceof MccTipoArchivo) {
			MccTipoArchivo mccTipoArchivo = (MccTipoArchivo) objects.get(i);
			catalogoDTO.setCveCatalogo(mccTipoArchivo.getCveIdTipoArchivo());
			catalogoDTO.setDescCatalogo(mccTipoArchivo.getDesArchivo());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccTipoRiesgo) {
			MccTipoRiesgo mccTipoRiesgo = (MccTipoRiesgo) objects.get(i);
			catalogoDTO.setCveCatalogo(mccTipoRiesgo.getCveIdTipoRegistro());
			catalogoDTO.setDescCatalogo(mccTipoRiesgo.getDesDescripcion());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccClase) {
			MccClase mccTipoRiesgo = (MccClase) objects.get(i);
			catalogoDTO.setCveCatalogo(mccTipoRiesgo.getCveIdClase());
			catalogoDTO.setDescCatalogo(mccTipoRiesgo.getDesClase());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccFraccion) {
			MccFraccion mccTipoRiesgo = (MccFraccion) objects.get(i);
			catalogoDTO.setCveCatalogo(mccTipoRiesgo.getCveFraccion());
			catalogoDTO.setDescCatalogo(mccTipoRiesgo.getDesFraccion());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccActoInseguro) {
			MccActoInseguro mccActoInseguro = (MccActoInseguro) objects.get(i);
			catalogoDTO.setCveCatalogo(mccActoInseguro.getCveIdActoInseguro());
			catalogoDTO.setDescCatalogo(mccActoInseguro.getDesDescripcion());
			catalogoDTOList.add(catalogoDTO);
		}
		if (objects.get(i) instanceof MccModificacionPatronal) {
			MccModificacionPatronal mccModificacionPatronal = (MccModificacionPatronal) objects.get(i);
			catalogoDTO.setCveCatalogo(mccModificacionPatronal.getCveIdModifPatron());
			catalogoDTO.setDescCatalogo(mccModificacionPatronal.getDesModifPatron());
			catalogoDTOList.add(catalogoDTO);
		}
		
	}

}
