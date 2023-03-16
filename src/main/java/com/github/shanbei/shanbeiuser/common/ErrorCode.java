package com.github.shanbei.shanbeiuser.common;

import lombok.Getter;
import lombok.ToString;

/**
 * 错误码
 *
 * @author zhengqingquan
 */
@Getter
@ToString
public enum ErrorCode {

    /**
     * 返回值成功
     */
    SUCCESS(0, "ok", "返回值成功"),

    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000, "请求参数错误", "请求参数错误"),

    /**
     * 未登录错误
     */
    NOT_LOGIN_ERROR(40100, "未登录", "未登录错误"),

    /**
     * 没有权限错误
     */
    NO_AUTH_ERROR(40101, "没有权限", "没有权限错误"),

    /**
     * 禁止访问错误
     */
    FORBIDDEN_ERROR(40300, "禁止访问", "禁止访问错误"),

    /**
     * 请求数据不存在错误
     */
    NOT_FOUND_ERROR(40400, "请求数据不存在", "请求数据不存在错误"),

    /**
     * 系统内部异常错误
     */
    SYSTEM_ERROR(50000, "系统内部异常", "系统内部异常错误"),

    /**
     * 操作失败错误
     */
    OPERATION_ERROR(50001, "操作失败", "操作失败错误");

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误码信息
     */
    private final String message;

    /**
     * 错误码详情
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
