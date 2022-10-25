package com.etrade.analytic.repository;

import com.etrade.analytic.model.LoginEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginEventRepository extends MongoRepository<LoginEvent, String> {
}
