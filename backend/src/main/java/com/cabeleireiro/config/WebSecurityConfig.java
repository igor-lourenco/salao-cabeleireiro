package com.cabeleireiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private BCryptPasswordEncoder senhaEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception { // classe para liberar todos os endpoints
		web.ignoring().antMatchers("/actuator/**"); //pra passar pela biblioteca do oauth
	}

	//método pra busca o usuario e analisar a senha pra autenticar 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(senhaEncoder);
	}

	@Override
	@Bean // pra também ser um componente do sistema, e ser usado na hora de autenticar e ser o AuthorizationServer
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
}
