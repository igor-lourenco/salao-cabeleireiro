package com.cabeleireiro.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabeleireiro.dto.AgendamentoDTO;
import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.ItemPedido;
import com.cabeleireiro.entities.enums.HorarioEnum;
import com.cabeleireiro.repositories.AgendamentoRepository;
import com.cabeleireiro.repositories.ItemPedidoRepository;
import com.cabeleireiro.repositories.ServicoRepository;
import com.cabeleireiro.services.exceptions.DatabaseException;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository repository;
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private ItemPedidoRepository itemRepository;
	

	@Transactional(readOnly = true)
	public List<AgendamentoDTO> findAll() {
		List<Agendamento> entity = repository.findAll();
		return entity.stream().map(x -> new AgendamentoDTO(x, x.getItens())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AgendamentoDTO findById(Integer id) {
		Optional<Agendamento> entity = repository.findById(id);
		Agendamento obj = entity.orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado -> " + id));
		return new AgendamentoDTO(obj, obj.getItens());
	}

	@Transactional()
	public AgendamentoDTO insert(AgendamentoDTO dto) {
		Agendamento entity = new Agendamento();
		
		entity.setHorario(HorarioEnum.valueOf(dto.getHorario()));
		entity.setData(dto.getData());
		entity = repository.save(entity);
		
		for (ItemPedido ip : entity.getItens()) {
			ip.setServico(servicoRepository.getOne(ip.getServico().getId()));
			ip.setPreco(ip.getServico().getValor());
			ip.setPedido(entity);
		}
		itemRepository.saveAll(entity.getItens());	
		return new AgendamentoDTO(entity);
	}
/*
	@Transactional()
	public AgendamentoDTO update(Integer id, AgendamentoDTO dto) {
		Agendamento entity = repository.getOne(id);
		try {
			entity.setDescricao(dto.getDescricao());
			entity.setValor(dto.getValor());
			entity = repository.save(entity);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Serviço não foi atualizado -> " + id);
		}
		return new AgendamentoDTO(entity);
	}
	*/
	public void delete(Integer id) {
		try {
			repository.deleteById(id);			
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado -> " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco");
		}
	}
}
