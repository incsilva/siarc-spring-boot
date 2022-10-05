package br.edu.ifrn.jc.siarc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.ifrn.jc.siarc.PopulacaoInicialBanco;

@SpringBootTest
class SiarcApplicationTests {
	
	@MockBean
	PopulacaoInicialBanco populacaoInicialBanco;

	@Test
	void contextLoads() {
	}

}
