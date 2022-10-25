package com.etrade.analytic.repository;

import com.etrade.analytic.model.MessageClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageClickEventRepository extends MongoRepository<MessageClickEvent, String> {
}
