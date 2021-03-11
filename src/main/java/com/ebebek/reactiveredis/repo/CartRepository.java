package com.ebebek.reactiveredis.repo;

import com.ebebek.reactiveredis.model.cart.Cart;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CartRepository extends BaseRepository<Cart> {

    public CartRepository(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    public Cart save(Cart cart) {
        if(cart.getId() == null) {
            cart.setId(UUID.randomUUID().toString());
        }
        hashOperations.put(CARTS_KEY, cart.getId(), cart);
        return hashOperations.get(CARTS_KEY, cart.getId());
    }

}
