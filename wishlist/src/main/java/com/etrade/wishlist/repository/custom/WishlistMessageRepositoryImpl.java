package com.etrade.wishlist.repository.custom;

import com.etrade.core.model.WishlistMessage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.util.List;

public class WishlistMessageRepositoryImpl implements WishlistMessageRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public WishlistMessageRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<WishlistMessage> getOldWishlistMessages() {
        final Query query = new Query();
        query.addCriteria(Criteria.where("createDate").lte(LocalDate.now().plusDays(-2L)));
        return mongoTemplate.find(query, WishlistMessage.class, "wishlist-message");
    }
}
