package com.etrade.product.repository.custom;

import com.etrade.product.dto.FilterProductsRequest;
import com.etrade.product.dto.ListProductRequest;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ListProductRequest> filterProducts(FilterProductsRequest filterProductsRequest, int page);
}
