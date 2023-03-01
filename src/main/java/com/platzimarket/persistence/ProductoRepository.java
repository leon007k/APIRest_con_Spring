package com.platzimarket.persistence;

import com.platzimarket.domain.Product;
import com.platzimarket.domain.repository.ProductRepository;
import com.platzimarket.persistence.crud.ProductoCrudRepository;
import com.platzimarket.persistence.entity.Producto;
import com.platzimarket.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class ProductoRepository implements ProductRepository {

    private ProductoCrudRepository productoCrudRepository;
    private ProductMapper mapper;

    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(Long categoryId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Product>> getScarseProducts(Integer quantity) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    public List<Producto> getByCategoria(Long idCategoria){
        return productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategoria);
    }

    public Optional<List<Producto>> getEscasos(Integer cantidadStock){
        return productoCrudRepository.findBySantidadStockLessThanAndEstado(cantidadStock, true);
    }

    public Optional<Producto> getProducto(Long idProducto){
        return productoCrudRepository.findById(idProducto);
    }

    public Producto save(Producto producto){
        return productoCrudRepository.save(producto);
    }

    public void delete(Long idProducto){
        productoCrudRepository.deleteById(idProducto);
    }
}
