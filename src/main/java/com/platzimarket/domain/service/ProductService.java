package com.platzimarket.domain.service;

import com.platzimarket.domain.Product;
import com.platzimarket.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.getAll();
    }

    public Optional<Product> getProduct(Long productId){
        return productRepository.getProduct(productId);
    }

    public Optional<List<Product>> getByCategory(Long categoryId){
        return productRepository.getByCategory(categoryId);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public boolean delete(Long productId){
        return getProduct(productId).map(product -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);
    }

    public Optional<List<Product>> getProductsByName(String name){
        return productRepository.getProductsByName(name);
    }

    public Optional<List<Product>> getProductsBySalePrice(int priceSaleStart, int priceSaleEnd){
        return productRepository.getProductsBySalePrice(priceSaleStart, priceSaleEnd);
    }
}
