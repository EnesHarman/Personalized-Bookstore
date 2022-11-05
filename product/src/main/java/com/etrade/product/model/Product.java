package com.etrade.product.model;

import com.etrade.product.core.constants.ProductCategories;
import com.etrade.product.model.helpers.Links;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
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
    private String category;
}
