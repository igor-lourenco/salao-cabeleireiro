package com.cabeleireiro.dto;

import javax.validation.constraints.NotBlank;

public class ClienteInsertDTO extends ClienteDTO{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Senha obrigatória")
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
