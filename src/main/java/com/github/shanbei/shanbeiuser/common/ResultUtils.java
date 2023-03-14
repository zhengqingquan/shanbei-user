package com.github.shanbei.shanbeiuser.common;

/**
 * 返回工具类
 * 可以返回响应主题，简化返回的操作。
 *
 * @author zhengqingquan
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data 需要返回的数据
     * @param <T>  返回数据的类型
     * @return 通用返回类
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS, data);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 通用返回类
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }


    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 通用返回类
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     *
     * @param code 错误码
     * @param message 错误消息
     * @param description 错误描述
     * @return 通用返回类
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }
}
