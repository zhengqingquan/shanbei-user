package com.github.shanbei.shanbeiuser.service.impl;

import com.github.shanbei.shanbeiuser.model.domain.User;
import com.github.shanbei.shanbeiuser.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){

    }


    @Test
    void userRegister() {
        // 密码非空
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        // 账号长度小于4
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        // 密码小于8位
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        // 账号唯一
        userAccount="123";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        // 密码要和校验密码相同
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        // 注册账户
        userAccount="shanbei";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result>0);

    }

    @Test
    void userlogin() {
    }

    @Test
    void getSafetyUser() {
    }

    @Test
    void searchUserByTagsSQL() {
        List<String> tagNameList = Arrays.asList("Java","Python");
        List<User> userList = userService.searchUserByTagsSQL(tagNameList);
        assertNotNull(userList);
    }

    @Test
    void userLogout() {
    }

    @Test
    void searchUserByTagsJVM() {
        List<String> tagNameList = Arrays.asList("Java","Python");
        List<User> userList = userService.searchUserByTagsJVM(tagNameList);
        assertNotNull(userList);
    }
}