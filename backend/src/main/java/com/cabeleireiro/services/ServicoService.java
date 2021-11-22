package com.cabeleireiro.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabeleireiro.dto.ServicoDTO;
import com.cabeleireiro.entities.Servico;
import com.cabeleireiro.repositories.ServicoRepository;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository repository;
	
	@Transactional(readOnly = true)
	public List<ServicoDTO> findAll() {
		List<Servico> entity = repository.findAll();
		return entity.stream().map(x -> new ServicoDTO(x)).collect(Collectors.toList());
	}
	@Transactional(readOnly = true)
	public ServicoDTO findById(Integer id) {
		Optional<Servico> entity = repository.findById(id);
		Servico obj = entity.orElseThrow(() -> new ResourceNotFoundException("Servico não encontrado -> " + id));
		return new ServicoDTO(obj);
	}
	@Transactional()
	public ServicoDTO insert(ServicoDTO dto) {
		Servico entity = new Servico();
		entity.setDescricao(dto.getDescricao());
		entity.setValor(dto.getValor());
		entity = repository.save(entity);
		return new ServicoDTO(entity);
	}
}
