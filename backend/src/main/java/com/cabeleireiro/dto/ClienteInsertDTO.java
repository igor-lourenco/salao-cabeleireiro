package com.cabeleireiro.dto;

public class ClienteInsertDTO extends ClienteDTO{
	private static final long serialVersionUID = 1L;
	
	private String senha;
	
	public ClienteInsertDTO() {
		super();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
