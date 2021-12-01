package com.cabeleireiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig  {

	@Bean //  Bean são annotations de configurações de métodos
	public BCryptPasswordEncoder senhaEncoder() {
		return new BCryptPasswordEncoder(); // retorna uma instancia para ser utilixada em outras classes
	}
}
