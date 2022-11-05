package com.etrade.product.dto;

import com.etrade.product.model.helpers.Links;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterProductsRequest {
    private String isbn;
    private String title;
    private String author;
    private short minPageNum;
    private short maxPageNum;
    private int minPrice;
    private int maxPrice;
    private String publisher;
    private String category;
}
