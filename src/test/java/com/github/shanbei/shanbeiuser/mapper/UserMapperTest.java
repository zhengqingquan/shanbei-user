package com.github.shanbei.shanbeiuser.mapper;


import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 *
 * @author zhengqingquan
 */
@SpringBootTest
class UserMapperTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Resource
    private UserService userService;

    @Test
    void testAdduser() {
        User auser = new User();
        auser.setUsername("testShanbei");
        auser.setUserAccount("123");
        auser.setAvatarUrl("");
        auser.setGender(0);
        auser.setUserPassword("xxx");
        auser.setPhone("123");
        auser.setEmail("456");
//        System.out.println(auser.getId());
        boolean result = userService.save(auser);
        System.out.println(auser.getId());
        assertTrue(result);
    }

}