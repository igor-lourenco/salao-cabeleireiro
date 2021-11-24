package com.cabeleireiro.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cabeleireiro.entities.pk.ItemPedidoPK;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();
	
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {
	}

	public ItemPedido(Agendamento agendamento, Servico servico, Integer quantidade, Double preco) {
		id.setAgendamento(agendamento);
		id.setServico(servico);
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	public double getSubTotal() {
		return preco  * quantidade;
	}

	@JsonIgnore
	public Agendamento getAgendamento() {
		return id.getAgendamento();
	}
	
	public void setPedido(Agendamento agendamento) {
		id.setAgendamento(agendamento);
	}
	
	public Servico getServico() {
		return id.getServico();
	}
	
	public void setServico(Servico servico) {
		id.setServico(servico);
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return  preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
