package com.etrade.wishlist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "wishlist-user")
public class Wishlist {
    @Id
    private String id;
    private String productId;
    private String title;
    private String image;
    private String author;
    private String userId;
    private LocalDate createDate;
}
