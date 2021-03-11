package com.ebebek.reactiveredis.service;

import com.ebebek.reactiveredis.model.cart.Cart;
import com.ebebek.reactiveredis.model.product.Product;
import com.ebebek.reactiveredis.model.product.ProductResponse;
import com.ebebek.reactiveredis.util.ResponseCodesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService {

    private static final String PRODUCTS_KEY = Keys.PRODUCTS_KEY.label;

    @Autowired
    private RestTemplate restTemplate;

    private HashOperations<String, String, Product> hashOperations;

    public ProductService(RedisTemplate redisTemplate) {
        redisTemplate.setHashValueSerializer(serializerSetup());
        hashOperations = redisTemplate.opsForHash();
    }

    public ProductResponse getAll() {
        if(hashOperations.size(PRODUCTS_KEY) == 0) {
            ProductResponse resp = restTemplate.getForObject("http://localhost:9081/api/product/getAll", ProductResponse.class);
            if (!CollectionUtils.isEmpty(resp.getResults()))
                hashOperations.putAll(PRODUCTS_KEY, resp.getResults().stream()
                        .collect(Collectors.toMap(e -> e.getId().toString(), e -> e)));
            else
                return new ProductResponse(null, ResponseCodesUtil.ERROR.message, ResponseCodesUtil.ERROR.code);
        }
        return new ProductResponse(hashOperations.values(PRODUCTS_KEY), ResponseCodesUtil.SUCCESS.message, ResponseCodesUtil.SUCCESS.code);
    }

    public Product getProductById (Long id) {
        return hashOperations.get(PRODUCTS_KEY, id.toString());
    }
}
