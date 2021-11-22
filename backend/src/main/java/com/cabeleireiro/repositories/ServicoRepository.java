package com.cabeleireiro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabeleireiro.entities.Servico;

@Repository
public interface ServicoRepository  extends JpaRepository<Servico, Integer>{

}
