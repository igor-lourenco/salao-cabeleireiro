package com.cabeleireiro.services.exceptions;

public class ReservaNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ReservaNotFoundException(String msg) {
		super(msg);
	}
}
