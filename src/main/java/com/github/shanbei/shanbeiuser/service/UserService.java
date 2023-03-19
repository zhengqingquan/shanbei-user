package com.github.shanbei.shanbeiuser.service;

import com.github.shanbei.shanbeiuser.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request http请求
     * @return 返回脱敏后的账号信息
     */
    User userlogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user 用户信息
     * @return 安全的用户信息
     */
    User getSafetyUser(User user);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据tag搜索用户
     * 使用内存查询的方法
     *
     * @param tagNameList
     * @return
     */
    public List<User> searchUserByTagsJVM(List<String> tagNameList);

    /**
     * 根据tag搜索用户
     * 使用数据库查询的方法
     *
     * @param tagList
     * @return
     */
    List<User> searchUserByTagsSQL(List<String> tagList);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    boolean updateUser(User user,User LoginUser);

    /**
     * 获取当前用户信息
     *
     * @return
     */
    User getLoginUser(HttpServletRequest request);

}
