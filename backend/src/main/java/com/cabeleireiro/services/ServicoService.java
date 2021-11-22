package com.cabeleireiro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabeleireiro.dto.ServicoDTO;
import com.cabeleireiro.entities.Servico;
import com.cabeleireiro.repositories.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository repository;
	
	@Transactional
	public List<ServicoDTO> findAll() {
		List<Servico> entity = repository.findAll();
		return entity.stream().map(x -> new ServicoDTO(x)).collect(Collectors.toList());
	}
}
