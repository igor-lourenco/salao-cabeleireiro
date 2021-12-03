package com.cabeleireiro.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.cabeleireiro.dto.EmailDTO;
import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.services.exceptions.EmailException;
import com.cabeleireiro.services.interfaces.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class SendGridEmailService implements EmailService {

	private static Logger log = LoggerFactory.getLogger(SendGridEmailService.class);
	
	@Autowired
	private SendGrid sendGrid;
	
	@Value("${default.recipient}")
	private String remetente;

	@Override
	public void sendEmail(Agendamento entity) {

		EmailDTO dto = new EmailDTO();
		dto.setFromEmail(remetente);
		dto.setFromName("Agendamento | Cabeleireiro");
		//dto.setReplyTo(remetente);
		Email from = new Email(dto.getFromEmail(), dto.getFromName());// remetente
		
		dto.setTo("igor.lourencosantos@gmail.com");
		Email to = new Email(dto.getTo());// destinatário
		
		//Content content = new Content(dto.getContentType(), dto.getBody());// conteudo do email

		dto.setSubject("Agendamento confirmado! Código: " + entity.getId());
		dto.setBody(entity.toString());
		dto.setContentType("text/plain");
		Content content = new Content(dto.getContentType(), dto.getSubject() + dto.getBody());
		
		// getSubject -> assunto do email
		Mail mail = new Mail(from, dto.getSubject(), to, content);

		Request request = new Request();// pra enviar o email

		try {
			request.setMethod(Method.POST);//método do envio do email
			request.setEndpoint("mail/send");    //qual api deles o sendgrid vai chamar
			request.setBody(mail.build()); //qual o corpo que o email vai enviar			
			
			log.info("Enviando email para: " + dto.getTo());
			
			//pra receber a resposta do sendgrid, se enviou ou se deu erro no envio
			Response response = sendGrid.api(request);  
			
			if(response.getStatusCode() >= 400 && response.getStatusCode() <= 500) {
				log.error("Erro ao enviar o email: " + response.getBody());
				throw new EmailException(response.getBody());
			}
				log.info("Email enviado: " + response.getStatusCode());
				
		} catch (IOException e) {
			throw new EmailException(e.getMessage());
		}
	}
}
