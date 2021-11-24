package com.cabeleireiro.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.enums.HorarioEnum;

public class AgendamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private LocalDate data;
	private HorarioEnum horario;

	private ServicoDTO servicoDTO;

	public AgendamentoDTO() {

	}

	public AgendamentoDTO(Integer id, LocalDate data, HorarioEnum horario, ServicoDTO servicoDTO) {
		this.id = id;
		this.data = data;
		this.horario = horario;
		this.servicoDTO = servicoDTO;
	}

	public AgendamentoDTO(Agendamento obj) {
		this.id = obj.getId();
		this.data = obj.getData();
		this.horario = obj.getHorario();
		this.servicoDTO = new ServicoDTO(obj.getServico());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public HorarioEnum getHorario() {
		return horario;
	}
	public String getHora() {
		return horario.getHora();
	}

	public void setHorario(HorarioEnum horario) {
		this.horario = horario;
	}

	public ServicoDTO getServicoDTO() {
		return servicoDTO;
	}

	public void setServicoDTO(ServicoDTO servicoDTO) {
		this.servicoDTO = servicoDTO;
	}
}
