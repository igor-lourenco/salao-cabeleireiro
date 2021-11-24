package com.cabeleireiro.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.ItemPedido;
import com.cabeleireiro.entities.enums.HorarioEnum;

public class AgendamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private LocalDate data;
	private HorarioEnum horario;

	private Set<ItemPedido> itens = new HashSet<>();

	public AgendamentoDTO() {

	}

	public AgendamentoDTO(Integer id, LocalDate data, HorarioEnum horario) {
		this.id = id;
		this.data = data;
		this.horario = horario;
	}

	public AgendamentoDTO(Agendamento obj) {
		this.id = obj.getId();
		this.data = obj.getData();
		this.horario = obj.getHorario();
	}

	public AgendamentoDTO(Agendamento obj, Set<ItemPedido> itens) {
		this(obj);
		itens.forEach(item -> this.itens.add(item));
	}

	public Double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido ip : itens) {
			soma = soma + ip.getSubTotal();
		}

		return soma;
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

	public String getHorario() {
		return horario.getHora();
	}

	public void setHorario(HorarioEnum horario) {
		this.horario = horario;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}
}
