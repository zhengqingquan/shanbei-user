package com.github.shanbei.shanbeiuser.content;

/**
 * 用户常量
 * @author zhengqq
 */
public interface UserContent {

    /**
     * 用户登录态的键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 权限
     * 0为普通用户权限
     * 1为管理员权限
     */
    int DEFAULT_ROLE = 0;
    int ADMIN_ROLE = 1;
}
