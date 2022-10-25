package com.etrade.wishlist.repository.custom;

import com.etrade.wishlist.model.WishlistMessage;

import java.util.List;

public interface WishlistMessageRepositoryCustom {
    List<WishlistMessage> getOldWishlistMessages();
}
