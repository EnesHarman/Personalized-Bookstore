package com.etrade.wishlist.jobs;

import com.etrade.wishlist.repository.WishlistMessageRepository;
import com.etrade.wishlist.service.WishlistService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class WishlistJobs {
    private WishlistService wishlistService;

    public WishlistJobs(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @Scheduled(cron = "0 0 1 * * *") //Every Day at 1:00 am
    public void clearWishlistMessages(){
        wishlistService.clearOldWishlistMessages();
    }
}
