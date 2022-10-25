package com.etrade.analytic.repository;

import com.etrade.analytic.model.ProductClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductClickEventRepository extends MongoRepository<ProductClickEvent, String> {
}
