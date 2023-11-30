package mx.gob.imss.cit.mspmccatalogos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mx.gob.imss.cit.mspmccatalogos.services.MsPmcCatalagosService;

@SpringBootTest
class MsPmcCatalogosApplciationTest {

	@Autowired
	MsPmcCatalagosService catalogos;
	
	@Test
	void contextLoads() {
		assertNotNull(catalogos);
	}

}
