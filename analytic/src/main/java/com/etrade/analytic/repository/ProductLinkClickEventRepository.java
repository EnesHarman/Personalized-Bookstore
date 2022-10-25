package com.etrade.analytic.repository;

import com.etrade.analytic.model.ProductLinkClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductLinkClickEventRepository extends MongoRepository<ProductLinkClickEvent, String> {
}
