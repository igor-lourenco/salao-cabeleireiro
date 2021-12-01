package com.cabeleireiro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Cliente;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotBlank(message = "Nome obrigatório")
	private String nome;
	
	@Email(message = "Favor entrar e-mail válido")
	private String email;
	
	private List<AgendamentoFindDTO> agendamentosDTO = new ArrayList<>();
	
	private Set<RoleDTO> rolesDTO = new HashSet<>();
	
	public ClienteDTO() {
		
	}

	public ClienteDTO(Integer id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
		obj.getRoles().forEach(role -> rolesDTO.add(new RoleDTO(role)));
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

	public List<AgendamentoFindDTO> getAgendamentosDTO() {
		return agendamentosDTO;
	}

	public Set<RoleDTO> getRolesDTO() {
		return rolesDTO;
	}
}
