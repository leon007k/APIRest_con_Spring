package com.platzimarket.domain.repository;

import com.platzimarket.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAll();
    Optional<List<Product>> getByCategory(Long categoryId);
    Optional<List<Product>> getScarseProducts(Integer quantity);
    Optional<List<Product>> getProductsByName(String name);
    Optional<List<Product>> getProductsBySalePrice(int priceSaleStart, int priceSaleEnd);
    Optional<Product> getProduct(Long productId);
    Product save(Product product);
    void delete(Long productId);
}
