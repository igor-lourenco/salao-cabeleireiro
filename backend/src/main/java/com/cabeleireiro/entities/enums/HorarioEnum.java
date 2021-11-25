package com.cabeleireiro.entities.enums;

public enum HorarioEnum {

	HORARIO_1("08:00 as 08:20"),
	HORARIO_2("08:20 as 08:40"),
	HORARIO_3("08:40 as 09:00");
	
	private String hora;
	
	private HorarioEnum(String hora) {
		this.hora = hora;
	}
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}
