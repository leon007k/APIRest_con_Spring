package com.platzimarket.persistence.crud;

import com.platzimarket.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/*
* This class contains the queries generated with the Query Method
*/
public interface ProductoCrudRepository extends CrudRepository<Producto, Long> {

    List<Producto> findByIdCategoriaOrderByNombreAsc(Long idCategoria);
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(Integer cantidadStock,
                                                                  boolean estado);

    Optional<List<Producto>> findByNombreIs(String nombre);

    Optional<List<Producto>> findByPrecioVentaBetweenOrderByPrecioVenta(int precioVentaStart,
                                                                        int precioVentaEnd);
}
