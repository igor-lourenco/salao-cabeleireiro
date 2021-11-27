package com.cabeleireiro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Cliente;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	private String senha;
	
	private List<AgendamentoFindDTO> agendamentosDTO = new ArrayList<>();
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Integer id, String nome, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
	}
	public ClienteDTO(Cliente obj, List<Agendamento> agendamentos) {
		this(obj);
		agendamentos.forEach(agenda -> this.agendamentosDTO.add(new AgendamentoFindDTO(agenda)));
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<AgendamentoFindDTO> getAgendamentosDTO() {
		return agendamentosDTO;
	}

}
