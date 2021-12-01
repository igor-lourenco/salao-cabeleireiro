package com.cabeleireiro.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.cabeleireiro.dto.ClienteUpdateDTO;
import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.repositories.ClienteRepository;
import com.cabeleireiro.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdateValid, ClienteUpdateDTO> {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private HttpServletRequest request; // guarda as informações da requisição
	
	@Override
	public void initialize(ClienteUpdateValid ann) {
	}

	@Override
	public boolean isValid(ClienteUpdateDTO dto, ConstraintValidatorContext context) {
		
		//pega um mapa com os atributos da url
		@SuppressWarnings("unchecked")
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		int clienteId = Integer.parseInt(uriVars.get("id"));
		
		List<FieldMessage> lista = new ArrayList<>();
		Cliente cliente = repository.findByEmail(dto.getEmail());
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		//se o cliente não for null e se o id do cliente da requisição for diferente do id do email
		if(cliente != null && clienteId != cliente.getId()) {
			lista.add(new FieldMessage("email", "Esse e-mail já existe"));
		}
		
			for (FieldMessage e : lista) { // insere na lista de FieldMessage os erros da validação
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return lista.isEmpty();
	}
}
