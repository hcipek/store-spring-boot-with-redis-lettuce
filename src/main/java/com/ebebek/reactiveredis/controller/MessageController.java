package com.ebebek.reactiveredis.controller;

import com.ebebek.reactiveredis.model.cart.Cart;
import com.ebebek.reactiveredis.model.cart.CartRequest;
import com.ebebek.reactiveredis.model.cart.CartResponse;
import com.ebebek.reactiveredis.model.product.ProductRequest;
import com.ebebek.reactiveredis.service.CartService;
import com.ebebek.reactiveredis.service.RedisReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/redis/message")
public class MessageController {

    @Autowired
    private RedisReceiverService redisReceiverService;

    @GetMapping("/getNext")
    public String getNext() {
        return redisReceiverService.getNext();
    }

    @GetMapping("/getWithSize")
    public List<String> getWithSize(@RequestParam("size") int size) {
        return redisReceiverService.getWithSize(size);
    }

    @GetMapping("/getSize")
    public Long getSize() {
        return redisReceiverService.getSize();
    }



}