package com.etrade.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListWishlistResponse {
    private String id;
    private String productId;
    private String title;
    private String author;
    private String mainImage;
}
