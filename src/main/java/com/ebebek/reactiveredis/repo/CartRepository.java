//package com.ebebek.reactiveredis.repo;
//
//import com.ebebek.reactiveredis.model.cart.Cart;
//import com.ebebek.reactiveredis.model.cart.CartRequest;
//import com.ebebek.reactiveredis.model.cart.CartResponse;
//import com.ebebek.reactiveredis.model.product.ProductRequest;
//import com.ebebek.reactiveredis.util.ResponseCodesUtil;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Repository
//public class CartRepository {
//
//    private HashOperations<String, String, Cart> hashOperations;
//
//    private static final String CARTS_KEY = "Carts";
//
//    public CartRepository(RedisTemplate template) {
//        this.hashOperations = template.opsForHash();
//    }
//
//    public CartResponse createCart(@RequestBody CartRequest request) {
//        Cart cart = request.getCart();
//        cart.setId(UUID.randomUUID().toString());
//        Map<String, Cart> cartMap = Stream.of(
//                new AbstractMap.SimpleEntry<>(cart.getId(), cart)
//        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        hashOperations.putAll(CARTS_KEY, cartMap);
//        return new CartResponse(Collections.singletonList(cart), ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
//    }
//
//    @GetMapping("/getAllCarts")
//    public List<Cart> getAllCarts() {
//        return hashOperations.values(CARTS_KEY);
//    }
//
//    @GetMapping("/getCartById")
//    public Object getCartById(@RequestParam("id") String id) {
//        return hashOperations.get(CARTS_KEY, id);
//    }
//
//    @PostMapping("/modifyCart")
//    private CartResponse modifyCart(@RequestBody CartRequest request) {
//        hashOperations.put(CARTS_KEY, request.getCart().getId(), request.getCart());
//        return new CartResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
//    }
//
//    @PostMapping("/incrementProductCount")
//    private CartResponse incrementProductCount(@RequestBody ProductRequest request) {
//        Map<String, Cart> cartMap = hashOperations.entries(CARTS_KEY);
//        cartMap.get(request.getCartId()).getCartItemList().stream()
//                .filter(e -> e.getProduct().getId().equals(request.getProductId()))
//                .findFirst()
//                .get()
//                .increment();
//        hashOperations.put(CARTS_KEY, request.getCartId(), cartMap.get(request.getCartId()));
//        return new CartResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
//    }
//
//    @PostMapping("/decrementProductCount")
//    private CartResponse decrementProductCount(@RequestBody ProductRequest request) {
//        Cart cart = (Cart)hashOperations.get(CARTS_KEY, request.getCartId());
//        cart.getCartItemList().stream()
//                .filter(e -> e.getProduct().getId().equals(request.getProductId()))
//                .findFirst()
//                .get()
//                .decrement();
//        hashOperations.put(CARTS_KEY, cart.getId(), cart);
//        return new CartResponse(null, ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
//    }
//}
