package com.cabeleireiro.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cabeleireiro.dto.AgendamentoDTO;
import com.cabeleireiro.services.AgendamentoService;

@RestController
@RequestMapping(value = "/agendamentos")
public class AgendamentoResource {
	
	@Autowired
	private AgendamentoService service;
	
	@GetMapping(value = "/data")
	public ResponseEntity<List<AgendamentoDTO>> findByData(
			@RequestParam("localDate") 
		    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate data){
		List<AgendamentoDTO> obj = service.findByData(data);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping
	public ResponseEntity<List<AgendamentoDTO>> findAll(){
		List<AgendamentoDTO> obj = service.findAll();
		return ResponseEntity.ok().body(obj);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<AgendamentoDTO> findById(@PathVariable Integer id){
		AgendamentoDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	@PostMapping()
	public ResponseEntity<AgendamentoDTO> insert(@Valid @RequestBody AgendamentoDTO dto){
		AgendamentoDTO obj = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AgendamentoDTO> update(@Valid @PathVariable Integer id, @RequestBody AgendamentoDTO dto){
		AgendamentoDTO obj = service.update(id,dto);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AgendamentoDTO> update(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
