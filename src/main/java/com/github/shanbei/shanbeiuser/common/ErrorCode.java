package com.github.shanbei.shanbeiuser.common;

/**
 * 错误码
 *
 * @author zhengqingquan
 */
public enum ErrorCode {

    /**
     * 返回值成功
     */
    SUCCESS(0, "ok", ""),
    /**
     * 请求参数错误
     */
    PARAMS_ERROR(40000, "请求参数错误", ""),
    /**
     * 请求数据不存在错误
     */
    NULL_ERROR(40001, "请求数据不存在", ""),
    /**
     * 未登录错误
     */
    NOT_LOGIN_ERROR(40100, "未登录", ""),
    /**
     * 没有权限错误
     */
    NO_AUTH_ERROR(40101, "没有权限", ""),
    /**
     * 系统内部异常错误
     */
    SYSTEM_ERROR(50000, "系统内部异常", "");

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

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
