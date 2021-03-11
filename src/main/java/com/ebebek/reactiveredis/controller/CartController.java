package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.cart.Cart;
import com.ebebek.reactiveredis.model.cart.CartRequest;
import com.ebebek.reactiveredis.model.cart.CartResponse;
import com.ebebek.reactiveredis.model.product.ProductRequest;
import com.ebebek.reactiveredis.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("/addProductToCart")
    public CartResponse addProductToCart(@RequestBody ProductRequest request) {
        return cartService.addProductToCart(request);
    }

    @PostMapping("/addOrIncreaseProductCount")
    public CartResponse addOrIncreaseProductCount(@RequestBody ProductRequest request) {
        return cartService.addOrIncreaseProductCount(request);
    }


}