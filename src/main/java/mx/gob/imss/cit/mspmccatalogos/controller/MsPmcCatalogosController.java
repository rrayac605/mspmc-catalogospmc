package mx.gob.imss.cit.mspmccatalogos.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.imss.cit.mspmccatalogos.services.MsPmcCatalagosService;
import mx.gob.imss.cit.mspmccommons.dto.ErrorResponse;
import mx.gob.imss.cit.mspmccommons.enums.EnumHttpStatus;
import mx.gob.imss.cit.mspmccommons.exception.BusinessException;
import mx.gob.imss.cit.mspmccommons.integration.model.MsPmcCatalogoDTO;

@RestController
@RequestMapping("/mspmccatalogos/v1")
public class MsPmcCatalogosController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MsPmcCatalagosService msPmcCatalagosService;
	
    @RequestMapping("/health/ready")
    @ResponseStatus(HttpStatus.OK)
    public void ready() {
    	// Indica que el ms esta listo para recibir peticiones
    }

    @RequestMapping("/health/live")
    @ResponseStatus(HttpStatus.OK)
    public void live() {
    	//Indica que el ms esta vivo
    }
    
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @PostMapping(value = "/catalogos", produces=MediaType.APPLICATION_JSON_VALUE)
    public Object getRecursoCapaDos(@RequestBody MsPmcCatalogoInput input) throws ClassNotFoundException {
    	
    	Object respuesta = null;
    	
        logger.debug("mspmccatalogo service ready to return");
        
        List<MsPmcCatalogoDTO> modelo;
        
        try {	        
        	modelo = msPmcCatalagosService.consultarCatalogo(input);
	        respuesta = new ResponseEntity<List<MsPmcCatalogoDTO>>(modelo, HttpStatus.OK);
	    } catch (BusinessException bexc) {        	
        	ErrorResponse errorResponse = bexc.getErrorResponse();        	
        	int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());  
            respuesta = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired)); 
        } catch (ClassNotFoundException cnfe) {
        	ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SUCCESS_OK, "No data foound", "No catalog implemented");
        	int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
            respuesta = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
		}
        
        return respuesta;
    }
    
    /**********************************************************
    *********** RECIBE PARAMETRO DE BUSQUEDA ******************
    ***********************************************************/
    @CrossOrigin(origins = "*", allowedHeaders="*")
    @PostMapping(value = "/catalogosRex", produces=MediaType.APPLICATION_JSON_VALUE)
    public Object getRecursoCapaDosRex(@RequestBody MsPmcCatalogoInput input) throws ClassNotFoundException {
    	
    	Object respuesta = null;
    	
        logger.debug("*************  CATTALOGO REGEX  *******{}", input.getTextRegex());
        logger.debug("mspmccatalogo service ready to return");
        
        List<MsPmcCatalogoDTO> model;
        
        try {
	        model = msPmcCatalagosService.consultarCatalogoRegex(input);
	
	        respuesta = new ResponseEntity<List<MsPmcCatalogoDTO>>(model, HttpStatus.OK);
	        
        }
        catch (BusinessException be) {
        	
        	ErrorResponse errorResponse = be.getErrorResponse();
        	
        	int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
  
            respuesta = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
 
        }
        catch (ClassNotFoundException cne) {
        	ErrorResponse errorResponse = new ErrorResponse(EnumHttpStatus.SUCCESS_OK, "No data foound", "No catalog implemented");
        	
        	int numberHTTPDesired = Integer.parseInt(errorResponse.getCode());
  
            respuesta = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(numberHTTPDesired));
		}
        
        return respuesta;
    }
	
	
}
