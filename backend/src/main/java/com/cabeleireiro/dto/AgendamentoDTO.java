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
	private ClienteDTO clienteDTO;

	public AgendamentoDTO() {

	}

	public AgendamentoDTO(Integer id, LocalDate data, HorarioEnum horario, ServicoDTO servicoDTO, ClienteDTO clienteDTO) {
		this.id = id;
		this.data = data;
		this.horario = horario;
		this.servicoDTO = servicoDTO;
		this.clienteDTO = clienteDTO;
	}

	public AgendamentoDTO(Agendamento obj) {
		this.id = obj.getId();
		this.data = obj.getData();
		this.horario = obj.getHorario();
		this.servicoDTO = new ServicoDTO(obj.getServico());
		this.clienteDTO = new ClienteDTO(obj.getCliente());
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

	public ClienteDTO getClienteDTO() {
		return clienteDTO;
	}

	public void setClienteDTO(ClienteDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}
}
