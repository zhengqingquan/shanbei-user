package com.github.shanbei.shanbeiuser.once;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

import com.github.shanbei.shanbeiuser.mapper.UserMapper;
import com.github.shanbei.shanbeiuser.model.domain.User;
import org.elasticsearch.common.inject.internal.Stopwatch;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {
    @Resource
    private UserMapper userMapper;

    // 60，当前能同时允许多少线程
    // 1000，最大能运行多少个线程。
    // 10000,线程的存活时间。
    // 时间单位
    // 任务队列，同时可以往里面塞10000个任务。
    // 策略，默认任务干不完了会抛出异常。中断。也可以自己写，比如打个日志之类的。
    private ExecutorService executorService = new ThreadPoolExecutor(60, 1000,10000, TimeUnit.MINUTES,new ArrayBlockingQueue<>(10000));

    /**
     * cpu密集型，分配的核心线程数=cpu-1，其中1个还需要运行主线程
     * IO密集型，分配的核心线程数可以大于cpu核心数。
     */


    /**
     * 批量插入用户
     * fixedDelay延迟5秒，在任务执行完成后延迟5秒再执行这个任务。
     * initialDelay是初次执行前的时间。比如初次5秒后执行该任务。
     */
    //@Scheduled(fixedDelay = 5000)
    @Scheduled(initialDelay = 5000,fixedRate = Long.MAX_VALUE)
    public void doInsertUser(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100;
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            // 以下默认值由Generate all setter插件完成。

            user.setUsername("假用户");
            user.setUserAccount("fakeshanbei");
            user.setAvatarUrl("https://avatars.githubusercontent.com/u/55186872?v=4");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("");
            user.setEmail("");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setTags("");

            userMapper.insert(user);
        }
        // 可以使用mybaits puls的批量插入。这里是100条作为一批插入数据库。
        // 1000 性能在20秒10万条数据
        // 10000 性能在18秒10万条数据
        // 50000 性能在17.9秒在10万条数据
        //serverUser.saveBatch(userList,100)

        // 使用并发编程可以更快7秒10万条数据。
        stopWatch.stop();
        System.out.printf(String.valueOf(stopWatch.getTotalTimeMillis()));
    }

    /**
     * 并发批量插入用户
     * 使用并发编程可以更快7秒10万条数据。10组每组1万条，共10万条。7秒多
     * 20组 10万条，每组5000并发，6秒多
     * 这里的瓶颈可能和机器相关。
     */
    public void doConcurrencyInsertUsers(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        int batchSize = 5000;
        // 分10组
        int j=0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM/batchSize; i++) {
            List<User> userList =new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("假用户");
                user.setUserAccount("fakeshanbei");
                user.setAvatarUrl("https://avatars.githubusercontent.com/u/55186872?v=4");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("");
                user.setEmail("");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setTags("");
                userList.add(user);

                if (j%batchSize==0){
                    break;
                }
            }

            CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                System.out.println("threadName:"+Thread.currentThread().getName());
                serverUser.saveBatch(userList,batchSize );
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.printf(String.valueOf(stopWatch.getTotalTimeMillis()));
    }
}
