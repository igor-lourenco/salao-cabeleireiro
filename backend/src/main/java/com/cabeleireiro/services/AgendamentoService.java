package com.cabeleireiro.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabeleireiro.dto.AgendamentoDTO;
import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Cliente;
import com.cabeleireiro.entities.enums.HorarioEnum;
import com.cabeleireiro.repositories.AgendamentoRepository;
import com.cabeleireiro.repositories.ClienteRepository;
import com.cabeleireiro.repositories.ServicoRepository;
import com.cabeleireiro.services.exceptions.DatabaseException;
import com.cabeleireiro.services.exceptions.ReservaNotFoundException;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;
import com.cabeleireiro.services.interfaces.EmailService;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository repository;
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private EmailService emailService;

	@Transactional(readOnly = true)
	public List<AgendamentoDTO> findAll() {
		List<Agendamento> entity = repository.findAll(Sort.by("data").descending());
		return entity.stream().map(x -> new AgendamentoDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AgendamentoDTO findById(Integer id) {
		Agendamento entity = findAgendamento(id);
		return new AgendamentoDTO(entity);
	}

	@Transactional()
	public AgendamentoDTO insert(AgendamentoDTO dto) {
		Agendamento entity = new Agendamento();
		atualizaAgendamento(entity, dto);
		entity = repository.save(entity);
		emailService.sendEmail(entity);
		return new AgendamentoDTO(entity);
	}

	@Transactional()
	public AgendamentoDTO update(Integer id, AgendamentoDTO dto) {
		try {
			Agendamento entity = findAgendamento(id);
			authService.validaClienteOuAdmin(entity.getCliente().getId());
			atualizaAgendamento(entity, dto);
			entity = repository.save(entity);
			return new AgendamentoDTO(entity);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Agendamento não foi atualizado -> " + id);
		}
	}
	
	public void delete(Integer id) {
		try {//pega o cliente do agendamento pra excluir somente o dele
			Agendamento entity = findAgendamento(id);
			authService.validaClienteOuAdmin(entity.getCliente().getId());
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Agendamento não encontrado -> " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco");
		}
	}
	
	public Agendamento  findAgendamento(Integer agendamentoId) {
		Optional<Agendamento> agendamento = repository.findById(agendamentoId);
		Agendamento entity = agendamento
				.orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado -> " + agendamentoId));
		return entity;
	}

	public List<AgendamentoDTO> findByData(LocalDate data) {
		List<Agendamento> agenda = repository.findByData(data);
		return agenda.stream().map(x -> new AgendamentoDTO(x)).collect(Collectors.toList());
	}

	public void atualizaAgendamento(Agendamento entity, AgendamentoDTO dto) {
		Cliente cliente = authService.autenticado();
		entity.setHorario(dto.getHorario());
		verificaHorario(entity, dto); // verifica se já existe um horário reservado
		entity.setServico(servicoRepository.getOne(dto.getServicoDTO().getId()));
		entity.setCliente(clienteRepository.getOne(cliente.getId()));
	}

	public void verificaHorario(Agendamento entity, AgendamentoDTO dto) {
		List<Agendamento> agenda = repository.findAll();

		for (Agendamento obj : agenda) {
			LocalDate localDate1 = obj.getData();
			LocalDate localDate2 = dto.getData();
			HorarioEnum horarioEnum1 = obj.getHorario();
			HorarioEnum horarioEnum2 = dto.getHorario();
			
			try {
				if (localDate1.equals(localDate2) && horarioEnum1.equals(horarioEnum2) // se o horario for igual
						&& !entity.getId().equals(obj.getId())) {
					throw new ReservaNotFoundException("Esse horário já está reservado: " + horarioEnum2.getHora());
				}
			} catch (NullPointerException e) {
				throw new ReservaNotFoundException("Esse horário já está reservado: " + horarioEnum2.getHora());
			}
			entity.setData(dto.getData());
		}
	}
}
