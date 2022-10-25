package com.etrade.wishlist.repository;

import com.etrade.wishlist.model.WishlistMessage;
import com.etrade.wishlist.model.helpers.WishlistMessageType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistMessageRepository extends MongoRepository<WishlistMessage, String> {
    Optional<WishlistMessage> findByUserIdAndProductIdAndWishlistMessageType(String userId, String productId, WishlistMessageType wishlistMessageType);
    List<WishlistMessage> findByUserId(String userId, Pageable pageable);
}
