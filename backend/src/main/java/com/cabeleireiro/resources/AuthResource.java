package com.cabeleireiro.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabeleireiro.dto.EmailDTO;
import com.cabeleireiro.dto.EmailSenhaNovaDTO;
import com.cabeleireiro.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private AuthService service;
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<EmailDTO> forgot(@Valid @RequestBody EmailSenhaNovaDTO dto){
		service.enviarNovaSenha(dto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
