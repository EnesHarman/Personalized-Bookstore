package com.etrade.wishlist.service;

import com.etrade.wishlist.core.result.*;
import com.etrade.wishlist.dto.AddWishlistRequest;
import com.etrade.wishlist.dto.ListWishlistResponse;
import com.etrade.wishlist.model.Wishlist;
import com.etrade.wishlist.repository.WishlistMessageRepository;
import com.etrade.wishlist.repository.WishlistRepository;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService{
    private final WishlistRepository wishlistRepository;
    private final WishlistMessageRepository wishlistMessageRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, WishlistMessageRepository wishlistMessageRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistMessageRepository = wishlistMessageRepository;
    }

    @Override
    public Result addWishList(HttpServletRequest request, AddWishlistRequest addWishlistRequest) {
        Wishlist wishlist = Wishlist.builder()
                .userId(getUserEmailFromRequest(request))
                .productId(addWishlistRequest.getId())
                .author(addWishlistRequest.getAuthor())
                .title(addWishlistRequest.getTitle())
                .image(addWishlistRequest.getImage())
                .createDate(LocalDate.now())
                .build();
        wishlistRepository.save(wishlist);
        return new SuccessResult("Product has added to your wish list.");
    }

    @Override
    public DataResult<List<ListWishlistResponse>> listWishlist(HttpServletRequest request, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        List<Wishlist> wishlists = wishlistRepository.findAllByUserId(getUserEmailFromRequest(request), pageable);
        List<ListWishlistResponse> list = wishlists.stream().map(this::castToListWishlistResponse).collect(Collectors.toList());
        return new SuccessDataResult<>(list);
    }

    @Override
    public Result deleteWishlist(String id) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);
        if(!wishlist.isPresent()){
            return new ErrorResult("There is a problem with wishlist id. Please try again.");
        }
        wishlistRepository.delete(wishlist.get());
        return new SuccessResult("The item has removed from your wish list.");
    }

    private String getUserEmailFromRequest(HttpServletRequest request){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        JSONObject obj = new JSONObject(payload);
        return obj.getString("email");
    }

    private ListWishlistResponse castToListWishlistResponse(Wishlist wishlist){
        return ListWishlistResponse.builder()
                .id(wishlist.getId())
                .productId(wishlist.getProductId())
                .title(wishlist.getTitle())
                .author(wishlist.getAuthor())
                .mainImage(wishlist.getImage())
                .build();
    }
}
