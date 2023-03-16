package com.github.shanbei.shanbeiuser.model.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -1402934951188969854L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
