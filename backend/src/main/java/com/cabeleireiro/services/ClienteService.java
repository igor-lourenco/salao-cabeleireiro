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
import com.cabeleireiro.dto.ClienteDTO;
import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.repositories.AgendamentoRepository;
import com.cabeleireiro.repositories.ClienteRepository;
import com.cabeleireiro.services.exceptions.DatabaseException;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private AgendamentoRepository agendaRepository;

	@Transactional(readOnly = true)
	public List<ClienteDTO> findAll() {
		List<Cliente> entity = repository.findAll();
		return entity.stream().map(x -> new ClienteDTO(x, x.getAgendamentos())).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClienteDTO findById(Integer id) {
		Optional<Cliente> entity = repository.findById(id);
		Cliente obj = entity.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado -> " + id));
		return new ClienteDTO(obj, obj.getAgendamentos());
	}

	@Transactional()
	public ClienteDTO insert(ClienteDTO dto) {
		Cliente entity = new Cliente();
		atualizaCliente(entity, dto);
		entity = repository.save(entity);
		return new ClienteDTO(entity);
	}

	@Transactional()
	public ClienteDTO update(Integer id, ClienteDTO dto) {
		Cliente entity = repository.getOne(id);
		try {
			atualizaCliente(entity, dto);
			entity = repository.save(entity);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Cliente não foi atualizado -> " + id);
		}
		return new ClienteDTO(entity);
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cliente não encontrado -> " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco");
		}
	}

	public void atualizaCliente(Cliente entity, ClienteDTO dto) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setSenha(dto.getSenha());

		for (AgendamentoDTO agendaDTO : dto.getAgendamentosDTO()) {
			Agendamento agenda = agendaRepository.getOne(agendaDTO.getId());
			entity.getAgendamentos().add(agenda);
		}
	}
}
