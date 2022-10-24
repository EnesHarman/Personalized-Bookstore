package com.etrade.wishlist.repository;

import com.etrade.wishlist.model.WishlistMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistMessageRepository extends MongoRepository<WishlistMessage, String> {
}
