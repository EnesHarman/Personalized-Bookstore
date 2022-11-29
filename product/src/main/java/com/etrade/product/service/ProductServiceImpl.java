package com.etrade.product.service;

import com.etrade.product.core.config.kafka.events.ProductEvent;
import com.etrade.product.core.constants.ProductEventTypes;
import com.etrade.core.result.*;
import com.etrade.product.dto.AddProductRequest;
import com.etrade.product.dto.FilterProductsRequest;
import com.etrade.product.dto.ListProductRequest;
import com.etrade.core.model.Product;
import com.etrade.core.model.helpers.Links;
import com.etrade.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Value(value = "${kafka.book-topic}")
    private String topicName;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public ProductServiceImpl(ProductRepository productRepository, KafkaTemplate<String, ProductEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
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
                .category(productRequest.getCategory())
                .stock(productRequest.getStock())
                .build();

        try {
            productRepository.save(product);
            return new SuccessResult("Product added");
        }catch (DuplicateKeyException e){
            return new ErrorResult("There is already a product with that isbn. Please check your inputs.");
        }
        catch (Exception e){
            return new ErrorResult("Product could not added. Please check your inputs.");
        }
    }

    @Override
    public Result addProduct(List<AddProductRequest> productRequestList) {
        List<String> genres = Arrays.asList("Horror", "Historical", "Romance", "Fantasy", "Literature");
        productRequestList.stream().forEach(productRequest -> {
            Random ran = new Random();
            int genreIndex = ran.nextInt(5);
            short pageNum = (short) (ran.nextInt(100) + 100);
            double price = ran.nextInt(100)+200;
            int stock = ran.nextInt(10) + 20;

            productRequest.setCategory(genres.get(genreIndex));
            productRequest.setImages(Arrays.asList("https://printablep.com/uploads/pinterest/book-genres-kids-printables_pin_210731.png"));

            Links links = Links.builder().hepsiBuradaLink("https://www.hepsiburada.com/bir-ask-masali-ahmet-umit-p-HBCV00002Z40TK")
                    .trendyolLink("https://www.trendyol.com/destek-yayinlari/vazgecilmez-olmanin-sirri-esra-ezmeci-p-368344931?boutiqueId=614935&merchantId=188606")
                    .build();
            productRequest.setLinks(links);

            productRequest.setPageNum(pageNum);

            productRequest.setPrice(price);

            productRequest.setStock(stock);

            addProduct(productRequest);
        });
        return new SuccessResult("Product collection stored.");
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
        product.get().setCategory(updateRequest.getCategory());

        productRepository.save(product.get());
        return new SuccessResult("Product updated");
    }

    @Override
    public DataResult<List<ListProductRequest>> listProducts(int page) {
        Pageable pageable = PageRequest.of(page - 1,20);
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

    @Override
    public Result addStock(Optional<String> id, int stockCount) {
        if(!id.isPresent() || stockCount <= 0){
            return new ErrorResult("Please check your stock value.");
        }
        Product product = productRepository.findById(id.get()).get();
        product.setStock(product.getStock() + stockCount);
        productRepository.save(product);
        kafkaTemplate.send(topicName, castToProductEvent(product, 0, ProductEventTypes.STOCK));
        return new SuccessResult("Stock of product has increased.");
    }

    @Override
    public Result makeDiscount(Optional<String> id, int discountCount) {
        if(!id.isPresent() || discountCount <= 0){
            return new ErrorResult("Please check your discount value.");
        }
        Product product = productRepository.findById(id.get()).get();
        product.setPrice(product.getPrice() - (product.getPrice() / 100 * discountCount));
        productRepository.save(product);
        kafkaTemplate.send(topicName, castToProductEvent(product, discountCount, ProductEventTypes.DISCOUNT));
        return new SuccessResult("New product price has been setting.");
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
                        .stock(product.getStock())
                        .publisher(product.getPublisher())
                        .images(product.getImages())
                        .build()).collect(Collectors.toList());
    }

    private ProductEvent castToProductEvent(Product product, int discount, String type){
        return ProductEvent.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .image(product.getMainImage())
                .discount(discount)
                .type(type)
                .build();
    }
}
