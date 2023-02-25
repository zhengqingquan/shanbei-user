package com.github.shanbei.shanbeiuser.service;

import com.github.shanbei.shanbeiuser.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 96400
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-02-23 23:02:11
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册功能
     * @param userAccount:   用户账号
     * @param userPassword:  用户密码
     * @param checkPassword: 校验密码
     * @return: 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request http请求
     * @return 返回账户的脱敏信息
     */
    User userlogin(String userAccount, String userPassword, HttpServletRequest request);
}
