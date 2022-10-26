package com.etrade.advice.model;

import com.etrade.advice.model.helpers.Links;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String id;
    private String isbn;
    private String title;
    private String author;
    private short pageNum;
    private Links links;
    private double price;
    private int stock;
    private String publisher;
    private String mainImage;
    private List<String> images;
}
