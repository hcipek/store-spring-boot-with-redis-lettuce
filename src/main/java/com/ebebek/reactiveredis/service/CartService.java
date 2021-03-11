package com.ebebek.reactiveredis.service;

import com.ebebek.reactiveredis.model.cart.Cart;
import com.ebebek.reactiveredis.model.cart.CartItem;
import com.ebebek.reactiveredis.model.cart.CartRequest;
import com.ebebek.reactiveredis.model.cart.CartResponse;
import com.ebebek.reactiveredis.model.product.Product;
import com.ebebek.reactiveredis.model.product.ProductRequest;
import com.ebebek.reactiveredis.repo.CartRepository;
import com.ebebek.reactiveredis.util.ResponseCodesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService extends BaseService {

    private static final String CARTS_KEY = "Carts";

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    private HashOperations<String, String, Cart> hashOperations;

    public CartService(RedisTemplate redisTemplate) {
        redisTemplate.setHashValueSerializer(serializerSetup());
        hashOperations = redisTemplate.opsForHash();
    }

    public CartResponse createCart(CartRequest request) {
        Cart cart = request.getCart();
        cart = cartRepository.save(cart);
        return new CartResponse(Collections.singletonList(cart), ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(String id) {
        return cartRepository.findById(id);
    }

    public CartResponse modifyCart(CartRequest request) {
        cartRepository.save(request.getCart());
        return new CartResponse(Collections.singletonList(request.getCart()), ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    public CartResponse incrementProductCount(ProductRequest request) {
        return changeProductCount(request, IncDec.INC);
    }

    public CartResponse decrementProductCount(ProductRequest request) {
        return changeProductCount(request, IncDec.DEC);
    }

    private CartResponse changeProductCount(ProductRequest request, IncDec incDec) {
        Cart cart = cartRepository.findById(request.getCartId());

        switch(incDec) {
            case INC :
                cart.getCartItemList().stream()
                        .filter(e -> e.getProduct().getId().equals(request.getProductId()))
                        .findFirst().orElse(new CartItem())
                        .increment();
                break;
            case DEC :
                cart.getCartItemList().stream()
                        .filter(e -> e.getProduct().getId().equals(request.getProductId()))
                        .findFirst().orElse(new CartItem())
                        .decrement();
                break;
        }

        cart.refresh();
        cartRepository.save(cart);
        return new CartResponse(Collections.singletonList(cart), ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    public CartResponse addProductToCart(ProductRequest request) {
        Product product = productService.getProductById(request.getProductId());
        Cart cart = null;

        if(product == null) {
            return new CartResponse(null, ResponseCodesUtil.ERROR.message, ResponseCodesUtil.ERROR.code);
        }

        if(request.getCartId() != null) {
            cart = cartRepository.findById(request.getCartId());
            if(cart == null) {
                return new CartResponse(null, ResponseCodesUtil.ERROR.message, ResponseCodesUtil.ERROR.code);
            }
        } else {
            cart = new Cart();
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCount(1);
        cart.addCartItem(cartItem);
        cart = cartRepository.save(cart);

        return new CartResponse(Collections.singletonList(cart), ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    public CartResponse addOrIncreaseProductCount(ProductRequest request) {
        if(request.getCartId() == null) {
            return addProductToCart(request);
        }
        Cart cart = cartRepository.findById(request.getCartId());
        if(cart == null) {
            return addProductToCart(request);
        }
        boolean isProductExists = cart.getCartItemList().stream()
                .filter(e -> e.getProduct().getId().equals(request.getProductId()))
                .findFirst()
                .isPresent();
        if(isProductExists) {
            return incrementProductCount(request);
        } else {
            return addProductToCart(request);
        }
    }

    enum IncDec {
        INC,
        DEC,
        INCx10,
        DECx10;
    }
}
