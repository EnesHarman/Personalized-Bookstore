package com.etrade.product.service;

import com.etrade.product.core.result.DataResult;
import com.etrade.product.core.result.Result;
import com.etrade.product.dto.AddProductRequest;
import com.etrade.product.dto.FilterProductsRequest;
import com.etrade.product.dto.ListProductRequest;
import com.etrade.product.model.helpers.Links;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Result addProduct(AddProductRequest productRequest);

    Result deleteProduct(Optional<String> id);

    Result updateProduct(Optional<String> id, AddProductRequest updateRequest);

    DataResult<List<ListProductRequest>> listProducts(int page);

    DataResult<List<ListProductRequest>> listByFilter(FilterProductsRequest filterProductsRequest, int page);

    Result addLink(Optional<String> id, Links links);
}
