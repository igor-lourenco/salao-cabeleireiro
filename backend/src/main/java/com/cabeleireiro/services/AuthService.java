package com.cabeleireiro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.repositories.ClienteRepository;
import com.cabeleireiro.services.exceptions.ForbiddenException;
import com.cabeleireiro.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository repository;
	
	public Cliente autenticado() {	
		try {
		//pega o email do usuario logado
		String clienteNome = SecurityContextHolder.getContext().getAuthentication().getName();
		return repository.findByEmail(clienteNome);
		}catch(Exception e) {
			throw new UnauthorizedException("Usuario inválido!!");
		}
	}
	
	public void validaClienteOuAdmin(Integer clienteId) {	
		Cliente cliente = autenticado();
		
		//se o usuario autenticado não for igual ao passado no parametro e tambem não for admin 
		if(!cliente.getId().equals(clienteId) && !cliente.HasRole("ROLE_ADMIN")) {
			throw new ForbiddenException("Acesso Negado");
		}
	}
}
