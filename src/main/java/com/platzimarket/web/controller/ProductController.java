package com.platzimarket.web.controller;

import com.platzimarket.domain.Product;
import com.platzimarket.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products marketPlatzi", description = "Product API")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static ResponseEntity<List<Product>> getListResponseEntity(List<Product> products) {
        return products != null && !products.isEmpty() ? new ResponseEntity<>(products, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Get All products")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                    schema = @Schema(implementation = Product.class)
            ))})
    @GetMapping()
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@Parameter(description = "id of product to be searched",
            required = true, example = "7") @PathVariable("id") Long productId){
        return ResponseEntity.of(productService.getProduct(productId));
    }

    @Operation(summary = "Get products by category id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",array = @ArraySchema(
                            schema = @Schema(implementation = Product.class)))}),
            @ApiResponse(responseCode = "400", description = "invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",content = @Content)
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Long categoryId){
        List<Product> products = productService.getByCategory(categoryId).orElse(null);
        return getListResponseEntity(products);
    }

    @Operation(summary = "Get products by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",array = @ArraySchema(
                            schema = @Schema(implementation = Product.class)))}),
            @ApiResponse(responseCode = "400", description = "invalid name", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",content = @Content)
    })
    @GetMapping("/productname")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam String name){
        List<Product> products = productService.getProductsByName(name).orElse(null);
        return getListResponseEntity(products);
    }

    @Operation(summary = "Get product by sales price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",array = @ArraySchema(
                            schema = @Schema(implementation = Product.class)))}),
            @ApiResponse(responseCode = "400", description = "invalid price", content = @Content),
            @ApiResponse(responseCode = "404", description = "Products not found",content = @Content)
    })
    @GetMapping("/productprice")
    public ResponseEntity<List<Product>> getProductsBySalesPrice(@RequestParam int priceStart, @RequestParam int priceEnd) {
        List<Product> products = productService.getProductsBySalePrice(priceStart, priceEnd).orElse(null);
        return getListResponseEntity(products);
    }

    @Operation(summary = "Add Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",array = @ArraySchema(
                            schema = @Schema(implementation = Product.class)))})
    })
    @PostMapping()
    public ResponseEntity<Product> save(@RequestBody Product product){
        return  new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",array = @ArraySchema(
                            schema = @Schema(implementation = Product.class)))}),
            @ApiResponse(responseCode = "400", description = "invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",content = @Content)
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long productId){
        return new ResponseEntity<>(productService.delete(productId) ?
                HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
