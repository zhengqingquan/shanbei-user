package com.github.shanbei.shanbeiuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * EnableScheduling可以开启Spring对定时任务的支持。也叫任务调度机制
 */
@SpringBootApplication
@MapperScan("com.github.shanbei.shanbeiuser.mapper")
@EnableScheduling
public class ShanbeiUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanbeiUserApplication.class, args);
    }

}
