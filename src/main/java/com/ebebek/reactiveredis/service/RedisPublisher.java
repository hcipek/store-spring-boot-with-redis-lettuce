package com.ebebek.reactiveredis.service;

import com.ebebek.reactiveredis.model.queue.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class RedisPublisher extends BaseService{
    private final ChannelTopic topic;
    private final AtomicLong counter = new AtomicLong(0);
    private final AtomicLong counter2 = new AtomicLong(0);

    public RedisPublisher(RedisTemplate template,
                              final ChannelTopic topic) {
        template.setValueSerializer(serializerSetup());
        this.redisTemplate = template;
        this.topic = topic;
    }

    @Scheduled(fixedDelay = 1000)
    public void publish() {
        ChannelTopic topic2 = new ChannelTopic("pubsub:info");
        log.info("Message sended to pubsub info");
        redisTemplate.convertAndSend(topic2.getTopic(), "Message "+ counter2.incrementAndGet());
    }

    @Scheduled(fixedDelay = 5000)
    public void publishQueue() {
        log.info("Message sended to pubsub queue");
        redisTemplate.convertAndSend(topic.getTopic(), createQueue().toString());
    }

    private Queue createQueue() {
        Queue queue = new Queue();
        Long value = counter.incrementAndGet();
        queue.setId(value);
        queue.setMessage("Message " + value + ", " + Thread.currentThread().getName());
        queue.setPublisher(topic.getTopic());

        return queue;
    }
}