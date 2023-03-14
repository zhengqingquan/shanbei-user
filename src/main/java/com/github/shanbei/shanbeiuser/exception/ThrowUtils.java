package com.github.shanbei.shanbeiuser.exception;


import com.github.shanbei.shanbeiuser.common.ErrorCode;

/**
 * 抛异常工具类
 *
 * @author zhengqingquan
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition 判断条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 判断条件
     * @param errorCode 错误码
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 判断条件
     * @param errorCode 异常代码
     * @param description 描述
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String description) {
        throwIf(condition, new BusinessException(errorCode, description));
    }
}
