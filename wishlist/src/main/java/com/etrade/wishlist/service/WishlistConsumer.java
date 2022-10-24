package com.etrade.wishlist.service;

import com.etrade.wishlist.core.config.kafka.events.ProductEvent;
import com.etrade.wishlist.core.constants.ProductEventTypes;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WishlistConsumer {
    private final WishlistService wishlistService;

    public WishlistConsumer(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @KafkaListener(topics = "products-topic")
    public void listenBooks (ProductEvent message){
        if(message.getType().equals(ProductEventTypes.DISCOUNT)){
            wishlistService.addDiscountWishlistMessage(message);
        }else if(message.getType().equals(ProductEventTypes.STOCK)){
            wishlistService.addStockWishlistMessage(message);
        }
    }
}
