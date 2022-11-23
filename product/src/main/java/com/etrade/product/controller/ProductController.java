package com.etrade.product.controller;

import com.etrade.core.result.*;
import com.etrade.product.dto.AddProductRequest;
import com.etrade.product.dto.FilterProductsRequest;
import com.etrade.product.dto.ListProductRequest;
import com.etrade.product.model.helpers.Links;
import com.etrade.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //TODO GET SINGLE PRODUCT

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody AddProductRequest productRequest){
        Result result = productService.addProduct(productRequest);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam Optional<String> id){
        Result result = productService.deleteProduct(id);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestParam Optional<String> id, @RequestBody AddProductRequest updateRequest){
        Result result = productService.updateProduct(id, updateRequest);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ListProductRequest>> listProducts(@RequestParam(defaultValue = "0") int page){
        DataResult<List<ListProductRequest>> result = productService.listProducts(page);
        return result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.badRequest().body(result.getData());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ListProductRequest>> listByFilter(@RequestBody FilterProductsRequest filterProductsRequest, @RequestParam(defaultValue = "0") int page){
        DataResult<List<ListProductRequest>> result = productService.listByFilter(filterProductsRequest, page);
        return result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.badRequest().body(result.getData());
    }

    @PutMapping("/add-link")
    public ResponseEntity<String> addLink(@RequestParam Optional<String> id, @RequestBody Links links){
        Result result = productService.addLink(id, links);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @PutMapping("/add-stock")
    public ResponseEntity<String> addStock(@RequestParam Optional<String> id, @RequestParam(defaultValue = "0") int stockCount){
        Result result = productService.addStock(id, stockCount);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @PutMapping("/discount")
    public ResponseEntity<String> makeDiscount(@RequestParam Optional<String> id, @RequestParam(defaultValue = "0") int discountCount){
        Result result = productService.makeDiscount(id, discountCount);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }


}
