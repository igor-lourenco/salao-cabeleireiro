package com.cabeleireiro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Servico;

public class ServicoDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Double valor;
	
	private List<AgendamentoDTO> agendamentoDTO = new ArrayList<>();
	
	public ServicoDTO() {
		
	}

	public ServicoDTO(Integer id, String descricao, Double valor) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public ServicoDTO(Servico obj) {
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.valor = obj.getValor();
	}
	
	public ServicoDTO(Servico obj, List<Agendamento> agendamento) {
		this(obj);
		agendamento.forEach(agenda -> this.agendamentoDTO.add(new AgendamentoDTO(agenda)));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public List<AgendamentoDTO> getAgendamentoDTO() {
		return agendamentoDTO;
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
		ServicoDTO other = (ServicoDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
