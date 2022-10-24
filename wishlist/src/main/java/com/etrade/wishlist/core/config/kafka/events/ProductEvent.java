package com.etrade.wishlist.core.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEvent {
    private String productId;
    private String title;
    private double price;
    private String image;
    private int discount;
    private String type;
}
