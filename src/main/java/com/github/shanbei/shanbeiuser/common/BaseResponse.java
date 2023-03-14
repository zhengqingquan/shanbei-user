package com.github.shanbei.shanbeiuser.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * 也叫响应信息主体
 *
 * @param <T> 泛型，返回的响应数据的类型。
 * @author zhengqingquan
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1381050981883960313L;

    /**
     * 响应的错误码
     */
    private int code;

    /**
     * 响应的数据
     */
    private T data;

    /**
     * 响应的信息
     */
    private String message;

    /**
     * 响应的描述
     */
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public BaseResponse(ErrorCode errorCode, T data) {
        this(errorCode.getCode(), data, errorCode.getMessage(), errorCode.getDescription());
    }
}
