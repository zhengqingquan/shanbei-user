package com.github.shanbei.shanbeiuser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    //@Bean
    //JedisConnectionFactory jedisConnectionFactory() {
    //    return new JedisConnectionFactory();
    //}
    //
    //@Bean
    //RedisTemplate<String, Object> redisTemplate() {
    //    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    //    return redisTemplate;
    //}

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> objectObjectRedisTemplate = new RedisTemplate<>();
        // 设置连接器
        redisTemplate().setConnectionFactory(connectionFactory);
        // 设置序列化器，否则在存入的时候Redis会有默认的序列化。
        objectObjectRedisTemplate.setKeySerializer(RedisSerializer.string());
        return objectObjectRedisTemplate;
    }
}