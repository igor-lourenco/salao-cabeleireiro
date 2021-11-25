package com.cabeleireiro.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.cabeleireiro.entities.enums.HorarioEnum;

@Converter(autoApply = true)
public class HorarioEnumConverter implements AttributeConverter<HorarioEnum, String> {
	// primeiro é o atributo da entidade, o segundo é o tipo que deseja armazenar no
	// banco
	
	@Override
	public String convertToDatabaseColumn(HorarioEnum horario) {
		switch (horario) {
		case HORARIO_1:
			return "08:00 as 08:20";
		case HORARIO_2:
			return "08:20 as 08:40";
		case HORARIO_3:
			return "08:40 as 09:00";

		default:
			throw new IllegalArgumentException("Horario " + horario + " não é suportada.");
		}
	}

	@Override
	public HorarioEnum convertToEntityAttribute(String dbData) { // converte para o banco
		switch (dbData) {
		case "08:00 as 08:20":
			return HorarioEnum.HORARIO_1;	
		case "08:20 as 08:40":
			return HorarioEnum.HORARIO_2;
		case "08:40 as 09:00":
			return HorarioEnum.HORARIO_3;

		default:
			throw new IllegalArgumentException("Horario " + dbData + " não é suportada.");
		}
	}
}
