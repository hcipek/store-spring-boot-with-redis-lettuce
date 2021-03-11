package com.ebebek.reactiveredis.repo;

import com.ebebek.reactiveredis.model.base.BaseModel;
import com.ebebek.reactiveredis.model.cart.Cart;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoRepositoryBean
public abstract class BaseRepository<T extends BaseModel> {

    protected static final String CARTS_KEY = "Carts";
    @Autowired
    protected RedisTemplate redisTemplate;
    protected HashOperations<String, String, T> hashOperations;


    public BaseRepository(RedisTemplate redisTemplate) {
        redisTemplate.setValueSerializer(serializerSetup());
        hashOperations = redisTemplate.opsForHash();
    }

    public void insertAll(Map<String, T> map) {
        hashOperations.putAll(CARTS_KEY, map);
    }

    public List<T> findAll() {
        return hashOperations.values(CARTS_KEY);
    }

    public T findById(String id) {
        return hashOperations.get(CARTS_KEY, id);
    }

    protected Jackson2JsonRedisSerializer serializerSetup() {
        Jackson2JsonRedisSerializer<BaseModel> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(BaseModel.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
