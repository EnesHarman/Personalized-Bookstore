package com.etrade.analytic.repository;

import com.etrade.core.model.MessageClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageClickEventRepository extends MongoRepository<MessageClickEvent, String> {
}
