package com.cabeleireiro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabeleireiro.entities.ItemPedido;

@Repository
public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, Integer>{

}
