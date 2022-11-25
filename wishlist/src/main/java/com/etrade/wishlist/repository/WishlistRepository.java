package com.etrade.wishlist.repository;

import com.etrade.core.model.Wishlist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String> {
    List<Wishlist> findAllByUserId(String userId, Pageable pageable);
    List<Wishlist> findAllByProductId(String productId);
}
