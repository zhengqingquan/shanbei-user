package com.github.shanbei.shanbeiuser.service.impl;

import com.github.shanbei.shanbeiuser.service.SysPostService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysPostServiceImplTest {

    @Resource
    private SysPostService sysPostService;

    @Test
    void selectAll() {
        System.out.println(sysPostService.selectAll());
    }

    @Test
    void addPost() {
    }

    @Test
    void removePost() {
    }

    @Test
    void selectPostById() {
        System.out.println(sysPostService.selectPostById(1L));
    }
}