package com.cabeleireiro.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabeleireiro.dto.ServicoDTO;
import com.cabeleireiro.services.ServicoService;

@RestController
@RequestMapping(value = "/servicos")
public class ServicoResource {
	
	@Autowired
	private ServicoService service;
	
	@GetMapping
	public ResponseEntity<List<ServicoDTO>> findAll(){
		List<ServicoDTO> obj = service.findAll();
		return ResponseEntity.ok().body(obj);
	}
}
