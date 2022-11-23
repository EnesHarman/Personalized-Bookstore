package com.etrade.advice.service;

import com.etrade.core.result.DataResult;
import com.etrade.core.result.Result;
import com.etrade.advice.dto.AdviceListCreateRequest;
import com.etrade.advice.dto.AdviceListResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdviceService {
    Result createAdviceList(AdviceListCreateRequest adviceListCreateRequest);

    DataResult<List<AdviceListResponse>> listAdviceLists();

    DataResult<AdviceListResponse> listAdvices(HttpServletRequest request);

    Result deleteAdviceList(String id);
}
