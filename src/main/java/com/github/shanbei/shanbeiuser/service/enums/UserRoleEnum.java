package com.github.shanbei.shanbeiuser.service.enums;

import com.github.shanbei.shanbeiuser.constant.UserContent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UserRoleEnum {

    /**
     * 普通用户
     */
    USER("用户", UserContent.DEFAULT_ROLE),

    /**
     * 管理员
     */
    ADMIN("管理员", UserContent.ADMIN_ROLE),

    /**
     * 被封号
     */
    BAN("被封号", UserContent.BAN_ROLE);

    private final String description;
    private final int role;


    UserRoleEnum(String description, int role) {
        this.description = description;
        this.role = role;
    }
}
