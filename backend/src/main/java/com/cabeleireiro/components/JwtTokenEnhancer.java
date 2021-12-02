package com.cabeleireiro.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.repositories.ClienteRepository;

@Component //classe pra implementar mais algumas informações do usuario no token
public class JwtTokenEnhancer implements TokenEnhancer{

	@Autowired
	private ClienteRepository repository;
	
	@Override // entra no ciclo de vida do token e adiciona os objetos passados
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		//o getName() é o email que está no authentication
		Cliente cliente = repository.findByEmail(authentication.getName());
		
		//map para pegar os dados do cliente que está no authentication
		Map<String, Object> map = new HashMap<>();
		map.put("clienteNome", cliente.getNome());
		map.put("clienteId", cliente.getId());
		
		//downcasting pra poder usar o método de adicionar as informações que está no map
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		
		//adiciona as informações que está no map no token
		token.setAdditionalInformation(map);
		
		return accessToken;
	} 
}
