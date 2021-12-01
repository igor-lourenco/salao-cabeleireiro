package com.cabeleireiro.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cabeleireiro.dto.ClienteInsertDTO;
import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.repositories.ClienteRepository;
import com.cabeleireiro.resources.exceptions.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsertValid, ClienteInsertDTO> {
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsertValid ann) {
	}

	@Override
	public boolean isValid(ClienteInsertDTO dto, ConstraintValidatorContext context) {
		
		List<FieldMessage> lista = new ArrayList<>();
		
		Cliente cliente = repository.findByEmail(dto.getEmail());
		
		// Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista
		
		if(cliente != null) {
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
