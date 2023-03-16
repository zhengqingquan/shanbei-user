package com.github.shanbei.shanbeiuser.model.domain.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisCacheTest {

    @Resource
    private RedisCache redisCache;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Test
    void testsetCacheObject() {
        // String key = "test_key";
        // String value = "test_value";
        // redisCache.setCacheObject(key, value);

        System.out.println((String) redisCache.getCacheObject("zqq"));

        // 查看redis信息
        // RedisConnection connection = redisConnectionFactory.getConnection();
        // Properties info = connection.info();
        // assert info != null;
        // System.out.println(info);
        // connection.close();


        // 查看redis信息
        // RedisConnection connection = redisConnectionFactory.getConnection();
        // RedisServerCommands serverCommands = connection.serverCommands();
        // for (Map.Entry<Object, Object> entry : serverCommands.getConfig("*").entrySet()) {
        //     String key = (String) entry.getKey();
        //     String value = (String) entry.getValue();
        //     System.out.println(key + " = " + value);
        // }

    }
}