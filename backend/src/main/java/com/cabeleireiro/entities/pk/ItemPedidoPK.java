package com.cabeleireiro.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Servico;

@Embeddable
public class ItemPedidoPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "agendamento_id")
	private Agendamento agendamento;
	
	@ManyToOne
	@JoinColumn(name = "servico_id")
	private Servico servico;
	
	public Agendamento getAgendamento() {
		return agendamento;
	}
	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico produto) {
		this.servico = produto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agendamento == null) ? 0 : agendamento.hashCode());
		result = prime * result + ((servico == null) ? 0 : servico.hashCode());
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
		ItemPedidoPK other = (ItemPedidoPK) obj;
		if (agendamento == null) {
			if (other.agendamento != null)
				return false;
		} else if (!agendamento.equals(other.agendamento))
			return false;
		if (servico == null) {
			if (other.servico != null)
				return false;
		} else if (!servico.equals(other.servico))
			return false;
		return true;
	}
}
