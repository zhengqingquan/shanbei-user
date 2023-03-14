package com.github.shanbei.shanbeiuser.exception;

import com.github.shanbei.shanbeiuser.common.BaseResponse;
import com.github.shanbei.shanbeiuser.common.ErrorCode;
import com.github.shanbei.shanbeiuser.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器。
 * RestControllerAdvice和ControllerAdvice是全局接口异常处理的类。
 * 当发生异常没有捕获时，便会触发这个异常。
 *
 * @author zhengqingquan
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常。
     * 这个方法只去捕获BusinessException异常。
     *
     * @param e 异常
     * @return 通用返回类
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse BusinessExceptionHandler(BusinessException e) {
        log.error("businessException" + e.getMessage(), e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), e.getDescription());
    }

    /**
     * 运行时异常。
     *
     * @param e 异常
     * @return 通用返回类
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse RuntimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "系统异常");
    }

}
