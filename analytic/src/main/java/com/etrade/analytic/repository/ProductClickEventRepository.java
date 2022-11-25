package com.etrade.analytic.repository;

import com.etrade.core.model.ProductClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductClickEventRepository extends MongoRepository<ProductClickEvent, String> {
}
