/**
 * 
 */
package mx.gob.imss.cit.mspmccatalogos.controller;

import lombok.Getter;
import lombok.Setter;

/**
 * @author roberto.raya
 *
 */
public class MsPmcCatalogoInput {
	
	@Getter
	@Setter
	private String nameCatalog;
	
	@Getter
	@Setter
	private String textRegex;

	@Getter
	@Setter
	private String searchField;

}
