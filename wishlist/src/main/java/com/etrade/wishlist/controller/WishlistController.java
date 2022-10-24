package com.etrade.wishlist.controller;

import com.etrade.wishlist.core.result.DataResult;
import com.etrade.wishlist.core.result.Result;
import com.etrade.wishlist.dto.AddWishlistRequest;
import com.etrade.wishlist.dto.ListWishlistResponse;
import com.etrade.wishlist.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addWishList(HttpServletRequest request, @RequestBody AddWishlistRequest addWishlistRequest){
        Result result = wishlistService.addWishList(request, addWishlistRequest);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }

    @GetMapping("/list")
    public ResponseEntity<List<ListWishlistResponse>> listWishlist(HttpServletRequest request, @RequestParam(defaultValue = "0") int page){
        DataResult<List<ListWishlistResponse>> result = wishlistService.listWishlist(request, page);
        return result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.badRequest().body(result.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWishlist(@PathVariable String id){
        Result result = wishlistService.deleteWishlist(id);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.badRequest().body(result.getMessage());
    }
}
