package com.etrade.wishlist.repository;

import com.etrade.wishlist.model.WishlistMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistMessageRepository extends MongoRepository<WishlistMessage, String> {
    Optional<WishlistMessage> findByUserIdAndProductId(String userId, String productId);
}
