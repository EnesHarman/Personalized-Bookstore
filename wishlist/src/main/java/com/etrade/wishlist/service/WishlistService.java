package com.etrade.wishlist.service;

import com.etrade.wishlist.core.result.DataResult;
import com.etrade.wishlist.core.result.Result;
import com.etrade.wishlist.dto.AddWishlistRequest;
import com.etrade.wishlist.dto.ListWishlistResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface WishlistService {
    Result addWishList(HttpServletRequest request, AddWishlistRequest addWishlistRequest);

    DataResult<List<ListWishlistResponse>> listWishlist(HttpServletRequest request, int page);

    Result deleteWishlist(String id);
}
