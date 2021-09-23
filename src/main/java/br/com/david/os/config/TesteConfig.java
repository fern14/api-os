package br.com.david.os.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.david.os.service.DBService;

@Configuration
@Profile("test")
public class TesteConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public void instanciaDb() {
		this.dbService.instanciaDb();

	}

}
