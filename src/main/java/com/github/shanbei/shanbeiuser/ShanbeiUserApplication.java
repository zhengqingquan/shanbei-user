package com.github.shanbei.shanbeiuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.shanbei.shanbeiuser.mapper")
public class ShanbeiUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShanbeiUserApplication.class, args);
	}

}
