package com.cabeleireiro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig  {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Bean //  Bean são annotations de configurações de métodos
	public BCryptPasswordEncoder senhaEncoder() {
		return new BCryptPasswordEncoder(); // retorna uma instancia para ser utilizada em outras classes
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtSecret); // registra o token com a nossa assinatura 
		return tokenConverter;
	}

	@Bean
	public JwtTokenStore tokenStore() { 
		return new JwtTokenStore(accessTokenConverter());
	}
}
