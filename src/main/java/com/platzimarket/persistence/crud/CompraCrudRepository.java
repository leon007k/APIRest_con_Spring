package com.platzimarket.persistence.crud;

import com.platzimarket.persistence.entity.Compra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompraCrudRepository extends CrudRepository<Compra, Long> {

    Optional<List<Compra>> getByIdCliente(String idCliente);
}
