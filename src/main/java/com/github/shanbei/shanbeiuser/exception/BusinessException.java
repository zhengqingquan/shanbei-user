package com.github.shanbei.shanbeiuser.exception;

import com.github.shanbei.shanbeiuser.common.ErrorCode;
import lombok.Getter;

/**
 * 业务异常类，也可以叫自定义异常类。
 * 这里继承RuntimeException是为了不用自己去捕获它。
 *
 * @author zhengqingquan
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    /**
     * 详细描述
     */
    private final String description;

    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), errorCode.getDescription());
    }

    public BusinessException(ErrorCode errorCode, String description) {
        this(errorCode.getCode(), errorCode.getMessage(), description);
    }
}
