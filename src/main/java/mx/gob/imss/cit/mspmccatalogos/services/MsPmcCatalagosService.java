package mx.gob.imss.cit.mspmccatalogos.services;

import java.util.List;

import mx.gob.imss.cit.mspmccatalogos.controller.MsPmcCatalogoInput;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.integration.model.MsPmcCatalogoDTO;

public interface MsPmcCatalagosService {

	   List<MsPmcCatalogoDTO> consultarCatalogo(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException;
	   List<MsPmcCatalogoDTO> consultarCatalogoRegex(MsPmcCatalogoInput input) throws BusinessException, ClassNotFoundException;



}
