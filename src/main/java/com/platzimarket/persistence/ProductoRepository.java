package com.platzimarket.persistence;

import com.platzimarket.domain.Product;
import com.platzimarket.domain.repository.ProductRepository;
import com.platzimarket.persistence.crud.ProductoCrudRepository;
import com.platzimarket.persistence.entity.Producto;
import com.platzimarket.persistence.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class ProductoRepository implements ProductRepository {

    @Autowired
    private ProductoCrudRepository productoCrudRepository;
    @Autowired
    private ProductMapper mapper;

    /**
     * @param producto
     * @return convert list of productos to products
     */
    private Optional<List<Product>> convertToProductList(Optional<List<Producto>> producto) {
        return producto.map(products -> mapper.toProducts(products));
    }

    @Override
    public List<Product> getAll(){
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(Long categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarseProducts(Integer quantity) {
        Optional<List<Producto>> productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return convertToProductList(productos);
    }

    @Override
    public Optional<List<Product>> getProductsByName(String name) {
        Optional<List<Producto>> producto = productoCrudRepository.findByNombreIs(name);
        return convertToProductList(producto);
    }

    @Override
    public Optional<List<Product>> getProductsBySalePrice(int priceSaleStart, int priceSaleEnd) {
        Optional<List<Producto>> productos = productoCrudRepository.findByPrecioVentaBetweenOrderByPrecioVenta(
                priceSaleStart, priceSaleEnd);
        return convertToProductList(productos);
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        return productoCrudRepository.findById(productId).map(producto -> mapper.toProduct(producto));
    }

    @Override
    public Product save(Product product) {
        Producto convertToProducto = mapper.toProducto(product);
        return mapper.toProduct(productoCrudRepository.save(convertToProducto));
    }

    @Override
    public void delete(Long productId){
        productoCrudRepository.deleteById(productId);
    }

     /*public List<Producto> getByCategoria(Long idCategoria){
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
    }*/
}
