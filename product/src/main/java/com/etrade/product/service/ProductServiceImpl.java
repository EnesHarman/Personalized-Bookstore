package com.etrade.product.service;

import com.etrade.product.core.result.*;
import com.etrade.product.dto.AddProductRequest;
import com.etrade.product.dto.FilterProductsRequest;
import com.etrade.product.dto.ListProductRequest;
import com.etrade.product.model.Product;
import com.etrade.product.model.helpers.Links;
import com.etrade.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Result addProduct(AddProductRequest productRequest) {
        Product product = Product.builder()
                .isbn(productRequest.getIsbn())
                .title(productRequest.getTitle())
                .author(productRequest.getAuthor())
                .pageNum(productRequest.getPageNum())
                .links(productRequest.getLinks())
                .price(productRequest.getPrice())
                .publisher(productRequest.getPublisher())
                .images(productRequest.getImages())
                .build();

        try {
            productRepository.save(product);
            return new SuccessResult("Product added");
        }catch (Exception e){
            System.out.println(e);
            return new ErrorResult("Product could not added. Please check your inputs.");
        }
    }

    @Override
    public Result deleteProduct(Optional<String> id) {
        if(!id.isPresent()){
            return new ErrorResult("Product id is not valid.");
        }
        try {
            productRepository.deleteById(id.get());
            return new SuccessResult("Product deleted");
        }catch (Exception e){
            System.out.println(e);
            return new ErrorResult("Product could not delete. Please check your inputs.");
        }
    }

    @Override
    public Result updateProduct(Optional<String> id, AddProductRequest updateRequest) {
        if(!id.isPresent()){
            return new ErrorResult("Product id is not valid.");
        }
        Optional<Product> product = productRepository.findById(id.get());
        if(!product.isPresent()){
            return new ErrorResult("Please provide valid product.");
        }

        product.get().setIsbn(updateRequest.getIsbn());
        product.get().setTitle(updateRequest.getTitle());
        product.get().setAuthor(updateRequest.getAuthor());
        product.get().setPageNum(updateRequest.getPageNum());
        product.get().setLinks(updateRequest.getLinks());
        product.get().setPrice(updateRequest.getPrice());
        product.get().setPublisher(updateRequest.getPublisher());
        product.get().setImages(updateRequest.getImages());

        productRepository.save(product.get());
        return new SuccessResult("Product updated");
    }

    @Override
    public DataResult<List<ListProductRequest>> listProducts(int page) {
        Pageable pageable = PageRequest.of(page,20);
        List<Product> products = productRepository.findAll(pageable).getContent();
        return new SuccessDataResult<>(castToListDto(products));
    }

    @Override
    public DataResult<List<ListProductRequest>> listByFilter(FilterProductsRequest filterProductsRequest, int page) {
        return new SuccessDataResult<>(productRepository.filterProducts(filterProductsRequest, page));
    }

    @Override
    public Result addLink(Optional<String> id, Links links) {
        if(!id.isPresent()){
            return new ErrorResult("There is a problem with product id. Please try again.");
        }
        Optional<Product> product = productRepository.findById(id.get());
        if(!product.isPresent()){
            return new ErrorResult("There is no such a product with that id. Please try again.");
        }

        product.get().setLinks(links);

        productRepository.save(product.get());
        return new SuccessResult("Links has set for the product.");

    }

    private List<ListProductRequest> castToListDto(List<Product> productList){
        return productList.stream()
                .map(product -> ListProductRequest.builder()
                        .id(product.getId())
                        .isbn(product.getIsbn())
                        .title(product.getTitle())
                        .author(product.getAuthor())
                        .pageNum(product.getPageNum())
                        .links(product.getLinks())
                        .price(product.getPrice())
                        .publisher(product.getPublisher())
                        .images(product.getImages())
                        .build()).collect(Collectors.toList());
    }
}
