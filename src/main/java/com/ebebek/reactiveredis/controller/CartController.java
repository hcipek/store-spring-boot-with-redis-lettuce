package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.cart.Cart;
import com.ebebek.reactiveredis.model.cart.CartItem;
import com.ebebek.reactiveredis.model.cart.CartRequest;
import com.ebebek.reactiveredis.model.cart.CartResponse;
import com.ebebek.reactiveredis.model.product.Product;
import com.ebebek.reactiveredis.model.product.ProductRequest;
import com.ebebek.reactiveredis.model.store.Store;
import com.ebebek.reactiveredis.model.store.StoreRequest;
import com.ebebek.reactiveredis.model.store.StoreResponse;
import com.ebebek.reactiveredis.model.ui.store.UIStoreRequest;
import com.ebebek.reactiveredis.service.CartService;
import com.ebebek.reactiveredis.util.ResponseCodesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/redis/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/createCart")
    public CartResponse createCart(@RequestBody CartRequest request) {
        return cartService.createCart(request);
    }

    @GetMapping("/getAllCarts")
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/getCartById")
    public Cart getCartById(@RequestParam("id") String id) {
        return cartService.getCartById(id);
    }

    @PostMapping("/modifyCart")
    public CartResponse modifyCart(@RequestBody CartRequest request) {
        return cartService.modifyCart(request);
    }

    @PostMapping("/incrementProductCount")
    public CartResponse incrementProductCount(@RequestBody ProductRequest request) {
        return cartService.incrementProductCount(request);
    }

    @PostMapping("/decrementProductCount")
    public CartResponse decrementProductCount(@RequestBody ProductRequest request) {
        return cartService.decrementProductCount(request);
    }


}