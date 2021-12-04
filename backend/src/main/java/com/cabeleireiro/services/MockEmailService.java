package com.cabeleireiro.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.services.interfaces.EmailService;

public class MockEmailService implements EmailService{

	private static Logger log = LoggerFactory.getLogger(SendGridEmailService.class);

	@Override
	public void sendEmail(Agendamento entity) {
		log.info("Simulando email...");
		log.info("Enviando email para:\n " + entity);
		log.info("Email enviado!!");
		
	}

	@Override
	public void enviarNovaSenhaEmail(Cliente cliente, String novaSenha) {
		log.info("Simulando email de nova senha...");
		log.info("Enviando nova senha para:\n " + cliente.getNome());
		log.info("Senha nova:\n " + novaSenha);
		log.info("\nEmail enviado!!");
		
	}
	

}
