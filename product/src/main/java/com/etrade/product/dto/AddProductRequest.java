package com.etrade.product.dto;

import com.etrade.product.model.helpers.Links;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {
    private String isbn;
    private String title;
    private String author;
    private short pageNum;
    private Links links;
    private double price;
    private int stock;
    private String publisher;
    private List<String> images;
    private String category;
}
