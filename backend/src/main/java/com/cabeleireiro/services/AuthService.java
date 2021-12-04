package com.cabeleireiro.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.repositories.ClienteRepository;
import com.cabeleireiro.services.exceptions.ForbiddenException;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;
import com.cabeleireiro.services.exceptions.UnauthorizedException;
import com.cabeleireiro.services.interfaces.EmailService;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private BCryptPasswordEncoder senhaEncoder;
	
	private Random random = new Random();
	
	public void enviarNovaSenha(String email) {
		Cliente cliente = repository.findByEmail(email);
		if(cliente == null) {
			throw new ResourceNotFoundException("Usuario não encontrado!! ");
		}	
		String novaSenha = novaSenha();
		cliente.setSenha(senhaEncoder.encode(novaSenha));
		repository.save(cliente);
		emailService.enviarNovaSenhaEmail(cliente, novaSenha);
	}
	
	
	private String novaSenha() {
		char[] senha = new char[10];
		for(int i = 0; i < senha.length; i++) {
			senha[i] = randomChar();
		}	
		return new String(senha);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) {  //gera digito
			return (char) (random.nextInt(10) + 48); //numero de 48 até 57
		}else if(opt == 1) { //gera letra maiuscula
			return (char) (random.nextInt(26) + 65); //letra de 65 até 90
		}
		else {  //gera letra minuscula
			return (char) (random.nextInt(26) + 97); //numero de 97 até 122
		}		
	}

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
