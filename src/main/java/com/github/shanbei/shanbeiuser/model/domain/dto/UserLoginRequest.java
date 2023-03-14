package com.github.shanbei.shanbeiuser.model.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -4252791596726319271L;

    private String userAccount;
    private String userPassword;
}
