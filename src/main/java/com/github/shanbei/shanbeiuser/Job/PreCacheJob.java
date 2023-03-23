package com.github.shanbei.shanbeiuser.Job;

import com.github.shanbei.shanbeiuser.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 预热缓存任务
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserMapper userMapper;

    // 引入分布式锁
    @Resource
    private RedissonClient redissonClient;

    // 每天执行，预热加载用户
    @Scheduled(cron = "0 58 23 * * *")
    public void doCacheRecommendUser(){
        // 获取锁的对象
        RLock lock = redissonClient.getLock("shanbei:precachejojb:docache:lock");
        // 写锁
        try {
            if (lock.tryLock(0,30000, TimeUnit.MINUTES)){

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        // 重点用户，ID为1的
        // List<Long> mainUserList = Arrays.asList(1L);
    }

}
