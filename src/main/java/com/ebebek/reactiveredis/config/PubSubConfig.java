package com.ebebek.reactiveredis.config;

import com.ebebek.reactiveredis.service.RedisPublisher;
import com.ebebek.reactiveredis.service.RedisReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Configuration
@EnableScheduling
public class PubSubConfig {

    private final RedisConnectionFactory factory;

    @Autowired
    private ChannelTopic topic;

    private MessageListenerAdapter adapter;
    private final RedisTemplate<String, Object> redisTemplate;

    public PubSubConfig(RedisConnectionFactory factory, final RedisTemplate template) {
        this.factory = factory;
        this.redisTemplate = template;
        this.adapter = new MessageListenerAdapter(new RedisReceiverService(this.redisTemplate));
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(factory);
        container.addMessageListener(adapter, topic);

        return container;
    }

    @Bean
    public RedisPublisher redisPublisher() {
        return new RedisPublisher(redisTemplate, topic);
    }
}
