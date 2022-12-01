package com.etrade.advice.repository;

import com.etrade.advice.dto.AdviceListResponse;
import com.etrade.core.model.Advice;
import com.etrade.advice.repository.custom.AdviceRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdviceRepository extends MongoRepository<Advice, String>, AdviceRepositoryCustom {
    AdviceListResponse getAdvicesInfo(Advice advice);

    Advice getUserAdviceList(String userEmail);

    Advice getDefaultAdvice();
}
