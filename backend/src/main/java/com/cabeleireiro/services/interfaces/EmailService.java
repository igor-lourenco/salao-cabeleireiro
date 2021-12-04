package com.cabeleireiro.services.interfaces;

import com.cabeleireiro.entities.Agendamento;
import com.cabeleireiro.entities.Cliente;

public interface EmailService {

	void sendEmail(Agendamento entity);
	void enviarNovaSenhaEmail(Cliente cliente, String novaSenha);
}
