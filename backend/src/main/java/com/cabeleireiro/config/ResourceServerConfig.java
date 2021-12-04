package com.cabeleireiro.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


@Configuration
@EnableResourceServer // pra implementar a funcionalidade do ResourceServerConfigurerAdapter do OAuth
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private Environment env;
	
	private static final String[] PUBLICO1 = {"/oauth/token/**", "/h2-console/**"};

	private static final String[] PUBLICO2 = {"/agendamentos/**", "/servicos/**"};
	
	private static final String[] NOVO_CLIENTE = {"/clientes/**"};

	private static final String[] SERVICO = {"/servicos/**"};

	private static final String[] AGENDAMENTO = {"/agendamentos/**"};
	
	private static final String[] NOVA_SENHA = {"/auth/forgot/**"};

	@Override // configura o tokenStore, pra decodificar o token e verificar se o token é válido
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//pra liberar o banco h2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
		.antMatchers(PUBLICO1).permitAll()
		.antMatchers(NOVA_SENHA).permitAll()
		.antMatchers(HttpMethod.GET, PUBLICO2).permitAll()
		.antMatchers(HttpMethod.POST, NOVO_CLIENTE).permitAll()
		.antMatchers(HttpMethod.POST, SERVICO).hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.POST, AGENDAMENTO).hasAnyRole("CLIENTE", "ADMIN")
		.antMatchers(HttpMethod.PUT, AGENDAMENTO).hasAnyRole("CLIENTE", "ADMIN")
		.antMatchers(HttpMethod.PUT, SERVICO).hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, SERVICO).hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/agendamentos/{id}**").hasAnyRole("ADMIN", "CLIENTE")
		.antMatchers(HttpMethod.GET, "/clientes/{id}/**").hasAnyRole("CLIENTE", "ADMIN")
		.anyRequest().hasRole("ADMIN");
	}
}
