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
import com.cabeleireiro.entities.enums.HorarioEnum;
import com.cabeleireiro.repositories.AgendamentoRepository;
import com.cabeleireiro.repositories.ServicoRepository;
import com.cabeleireiro.services.exceptions.DatabaseException;
import com.cabeleireiro.services.exceptions.ReservaNotFoundException;
import com.cabeleireiro.services.exceptions.ResourceNotFoundException;

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository repository;
	@Autowired
	private ServicoRepository servicoRepository;

	@Transactional(readOnly = true)
	public List<AgendamentoDTO> findAll() {
		List<Agendamento> entity = repository.findAll(Sort.by("data").descending());
		return entity.stream().map(x -> new AgendamentoDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public AgendamentoDTO findById(Integer id) {
		Optional<Agendamento> entity = repository.findById(id);
		Agendamento obj = entity.orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado -> " + id));
		return new AgendamentoDTO(obj);
	}

	@Transactional()
	public AgendamentoDTO insert(AgendamentoDTO dto) {
		Agendamento entity = new Agendamento();
		entity.setHorario(dto.getHorario());
		verificaHorario(dto);	 // verifica se já existe um horário reservado
		entity.setData(dto.getData());
		entity.setServico(servicoRepository.getOne(dto.getServicoDTO().getId()));
		entity = repository.save(entity);
		return new AgendamentoDTO(entity);
	}
	
	@Transactional()
	public AgendamentoDTO update(Integer id, AgendamentoDTO dto) {
		Agendamento entity = repository.getOne(id);
		try {
			entity.setHorario(dto.getHorario());
			verificaHorario(dto);        // verifica se já existe um horário reservado
			entity.setData(dto.getData());
			entity.setServico(servicoRepository.getOne(dto.getServicoDTO().getId()));
			entity = repository.save(entity);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Agendamento não foi atualizado -> " + id);
		}
		return new AgendamentoDTO(entity);
	}
	
	public void delete(Integer id) {
		try {
			repository.deleteById(id);			
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado -> " + id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco");
		}
	}
	
	public List<AgendamentoDTO> findByData(LocalDate data){
		List<Agendamento> agenda = repository.findByData(data);
		return agenda.stream().map(x -> new AgendamentoDTO(x)).collect(Collectors.toList());
	}
	
	public void verificaHorario(AgendamentoDTO dto) {
		List<Agendamento> agenda = repository.findAll();
		
		for (Agendamento obj : agenda) {
			LocalDate localDate1 = obj.getData();
			LocalDate localDate2 = dto.getData();
			HorarioEnum horarioEnum1 = obj.getHorario();
			HorarioEnum horarioEnum2 = dto.getHorario();
			
			if(localDate1.equals(localDate2) && horarioEnum1.equals(horarioEnum2)) {
				throw new ReservaNotFoundException("Esse horário já está reservado: " + horarioEnum2.getHora());
			}
		}
	}
}
