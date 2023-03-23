package com.github.shanbei.shanbeiuser.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    // 这里的名字要和yml中的一模一样
    // 可以在redis中自己去配置。一个redisson的字段。
    private String host;

    private String port;

    @Bean
    public RedissonClient redissonClient(){
        // 1. 创建配置
        // 1. Create config object
        Config config = new Config();
        //config.useClusterServers() // 这里使用了集群，但这里使用了本地一台服务器所以不使用这个
        String redisAddress = String.format("redis://%s:%s",host,port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(3);
                //// use "rediss://" for SSL connection
                //.addNodeAddress("redis://127.0.0.1:7181");

// or read config from file
//        config = Config.fromYAML(new File("config-file.yaml"));

        // 2. 创建redis实例
        // 2. Create Redisson instance

        // Sync and Async API 只要用这个
        RedissonClient redisson = Redisson.create(config);

        // Reactive API 不用关心
        //RedissonReactiveClient redissonReactive = redisson.reactive();

        // RxJava3 API 不用关心
        //RedissonRxClient redissonRx = redisson.rxJava();
        return redisson;
    }
}
