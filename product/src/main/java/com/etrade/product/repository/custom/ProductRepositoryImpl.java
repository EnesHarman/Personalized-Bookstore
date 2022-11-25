package com.etrade.product.repository.custom;

import com.etrade.product.dto.FilterProductsRequest;
import com.etrade.product.dto.ListProductRequest;
import com.etrade.core.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ListProductRequest> filterProducts(FilterProductsRequest filterProductsRequest, int page) {
        Pageable pageable = PageRequest.of(page - 1, 20);
        final Query query = new Query();
        final List<Criteria> criteria = new ArrayList<>();
        if(filterProductsRequest.getAuthor() != null) {
            query.addCriteria(Criteria.where("author").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    filterProductsRequest.getAuthor(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if(filterProductsRequest.getTitle() != null) {
            query.addCriteria(Criteria.where("title").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    filterProductsRequest.getTitle(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if(filterProductsRequest.getMaxPrice() != 0) {
            query.addCriteria(Criteria.where("price").lte(filterProductsRequest.getMaxPrice()));
        }
        if(filterProductsRequest.getMinPrice() != 0) {
            query.addCriteria(Criteria.where("price").gte(filterProductsRequest.getMinPrice()));
        }
        if(filterProductsRequest.getMaxPageNum() != 0) {
            query.addCriteria(Criteria.where("pageNum").lte(filterProductsRequest.getMaxPageNum()));
        }
        if(filterProductsRequest.getMinPageNum() != 0) {
            query.addCriteria(Criteria.where("pageNum").gte(filterProductsRequest.getMinPageNum()));
        }
        if(filterProductsRequest.getIsbn() != null) {
            query.addCriteria(Criteria.where("isbn").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    filterProductsRequest.getIsbn(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if(filterProductsRequest.getPublisher() != null) {
            query.addCriteria(Criteria.where("publisher").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    filterProductsRequest.getPublisher(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        if(filterProductsRequest.getCategory() != null) {
            query.addCriteria(Criteria.where("category").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    filterProductsRequest.getCategory(), MongoRegexCreator.MatchMode.CONTAINING
            ), "i"));
        }
        List<ListProductRequest> products = mongoTemplate.find(query, ListProductRequest.class, "products");
        return PageableExecutionUtils.getPage(
                products,
                pageable,
                ()->mongoTemplate.count(query, Product.class)
        ).getContent();
    }
}
