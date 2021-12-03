package com.cabeleireiro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cabeleireiro.entities.enums.HorarioEnum;

@Entity
@Table(name = "tb_agendamento")
public class Agendamento implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private LocalDate data;
	
	private HorarioEnum horario;
	
	@ManyToOne
	@JoinColumn(name = "servico_id")
	private Servico servico;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	public Agendamento() {
		
	}
	
	public Agendamento(Integer id, LocalDate data, HorarioEnum horario, Servico servico, Cliente cliente) {
		this.id = id;
		this.data = data;
		this.horario = horario;
		this.servico = servico;
		this.cliente= cliente;
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
	
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		StringBuilder builder = new StringBuilder();
		builder.append("\nAgendamento:\n");
		builder.append("Nome: " + cliente.getNome());
		builder.append("\n");
		builder.append("Data: ");
		builder.append(data.format(formatter));
		builder.append("\nHorário: ");
		builder.append(horario.getHora());
		builder.append("\nServiço: ");
		builder.append(servico.getDescricao());
		builder.append("\nValor: ");
		builder.append(servico.getValor());
		return builder.toString();
	}
}
