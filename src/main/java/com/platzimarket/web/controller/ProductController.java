package com.platzimarket.web.controller;

import com.platzimarket.domain.Product;
import com.platzimarket.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static ResponseEntity<List<Product>> getListResponseEntity(List<Product> products) {
        return products != null && !products.isEmpty() ? new ResponseEntity<>(products, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping()
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId){
        return ResponseEntity.of(productService.getProduct(productId));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Long categoryId){
        List<Product> products = productService.getByCategory(categoryId).orElse(null);
        return getListResponseEntity(products);
    }

    @GetMapping("/productname")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name){
        List<Product> products = productService.getProductsByName(name).orElse(null);
        return getListResponseEntity(products);
    }

    @GetMapping("/productprice")
    public ResponseEntity<List<Product>> getProductsBySalesPrice(@RequestParam int priceStart, @RequestParam int priceEnd) {
        List<Product> products = productService.getProductsBySalePrice(priceStart, priceEnd).orElse(null);
        return getListResponseEntity(products);
    }

    @PostMapping()
    public ResponseEntity<Product> save(@RequestBody Product product){
        return  new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long productId){
        return new ResponseEntity<>(productService.delete(productId) ?
                HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
