package com.cabeleireiro.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabeleireiro.entities.Agendamento;

@Repository
public interface AgendamentoRepository  extends JpaRepository<Agendamento, Integer>{

	List<Agendamento> findByData(LocalDate data);
}
