package com.etrade.advice.repository.custom;

import com.etrade.advice.dto.AdviceListResponse;
import com.etrade.advice.model.Advice;

import java.util.List;

public interface AdviceRepositoryCustom {
    AdviceListResponse getAdvicesInfo(Advice advice);
    Advice getUserAdviceList(String userEmail);


}