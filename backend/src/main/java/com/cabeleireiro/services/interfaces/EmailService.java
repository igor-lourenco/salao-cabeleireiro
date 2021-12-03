package com.cabeleireiro.services.interfaces;

import com.cabeleireiro.entities.Agendamento;

public interface EmailService {

	void sendEmail(Agendamento entity);
}
