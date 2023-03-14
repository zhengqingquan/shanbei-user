package com.github.shanbei.shanbeiuser.constant;

/**
 * 用户常量
 *
 * @author zhengqq
 */
public interface UserContent {

    /**
     * 用户登录态的键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户权限
     * 0 - 普通用户权限
     * 1 - 管理员权限
     */
    int DEFAULT_ROLE = 0;
    int ADMIN_ROLE = 1;
}
