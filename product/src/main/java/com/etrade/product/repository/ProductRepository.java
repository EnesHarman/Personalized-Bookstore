package com.etrade.product.repository;

import com.etrade.product.dto.ListProductRequest;
import com.etrade.core.model.Product;
import com.etrade.product.repository.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

}
