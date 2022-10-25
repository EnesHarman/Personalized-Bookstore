package com.etrade.analytic.repository;

import com.etrade.analytic.model.WlMessageClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WlMessageClickRepository extends MongoRepository<WlMessageClickEvent, String> {
}
