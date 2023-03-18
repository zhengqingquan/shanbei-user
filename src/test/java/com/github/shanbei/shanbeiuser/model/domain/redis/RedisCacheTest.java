package com.github.shanbei.shanbeiuser.model.domain.redis;

import com.github.shanbei.shanbeiuser.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Time;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisCacheTest {

    @Resource
    private RedisCache redisCache;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Test
    void testsetCacheObject() throws InterruptedException {
        // String key = "test_key";
        // String value = "test_value";
        // redisCache.setCacheObject(key, value);

        System.out.println(Constants.CAPTCHA_EXPIRATION);
        // redisCache.setCacheObject("key1","value1",1, TimeUnit.MINUTES);
        // System.out.println((String) redisCache.getCacheObject("key1"));
        // int a=0;
        // while (redisCache.getCacheObject("key1")!=null){
        //     TimeUnit.SECONDS.sleep(5); // 延迟1秒钟
        //     System.out.println((String) redisCache.getCacheObject("key1")+a);
        // }
        // System.out.println("结束");

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