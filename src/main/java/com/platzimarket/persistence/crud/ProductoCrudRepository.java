package com.platzimarket.persistence.crud;

import com.platzimarket.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoCrudRepository extends CrudRepository<Producto, Long> {

    List<Producto> findByIdCategoriaOrderByNombreAsc(Long idCategoria);
    Optional<List<Producto>> findBySantidadStockLessThanAndEstado(Integer cantidadStock,
                                                                  boolean estado);
}
