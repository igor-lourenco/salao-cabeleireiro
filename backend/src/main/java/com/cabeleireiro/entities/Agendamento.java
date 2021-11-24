package com.cabeleireiro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cabeleireiro.entities.enums.HorarioEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_agendamento")
public class Agendamento implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate data;
	
	private HorarioEnum horario;
	
	@OneToMany(mappedBy = "id.agendamento")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Agendamento() {
		
	}
	
	public Agendamento(Integer id, LocalDate data, HorarioEnum horario) {
		this.id = id;
		this.data = data;
		this.horario = horario;
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

	public HorarioEnum getHorario() {
		return horario;
	}

	public void setHorario(HorarioEnum horario) {
		this.horario = horario;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
