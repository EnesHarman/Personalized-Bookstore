package com.etrade.product.core.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEvent {
    private String id;
    private String title;
    private double price;
    private String image;
    private int discount;
}
