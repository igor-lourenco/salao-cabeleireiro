package com.cabeleireiro.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.cabeleireiro.components.JwtTokenEnhancer;

@Configuration
@EnableAuthorizationServer //faz o processamento pra ser o AuthorizationServer do oauth
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.client.client-id}")
	private String clienteId;
	@Value("${security.oauth2.client.client-secret}")
	private String clienteSecret;
	@Value("${jwt.duration}")
	private Integer duracao;
	
	@Autowired
	private BCryptPasswordEncoder senhaEncoder;
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	@Autowired
	private JwtTokenStore tokenStore;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private  JwtTokenEnhancer jwtTokenEnhancer;
	@Autowired
	private  UserDetailsService service;

	
	@Override 
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override // define como que vai ser a autentica????o e quais v??o ser os dados da aplica????o
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()                      // pro processo ser feito em mem??ria
		.withClient(clienteId)             // qual dizer qual vai ser o nome da aplica????o
		.secret(senhaEncoder.encode(clienteSecret))   // senha da aplica????o
		.scopes("read", "write")                       //se o acesso vai ser de leitura ou escrita ou os dois
		.authorizedGrantTypes("password", "refresh_token")               //grandType que vai no cabe??alho da autentica????o
		.accessTokenValiditySeconds(duracao)						//tempo pra expirar o token
		.refreshTokenValiditySeconds(duracao);                //tempo pra expirar o refresh token
		
	}

	@Override  // met??do pra dizer quem vai autorizar e qual vai ser o formato do token
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//pra pegar os dados do usuario e colocar no token
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, jwtTokenEnhancer));
		
		endpoints.authenticationManager(authenticationManager) // quem vai autenticar
		.tokenStore(tokenStore)     // token da aplica????o
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(chain)  // dados do usuario no token
		.userDetailsService(service); //pra pegar o refresh token
	}
}
