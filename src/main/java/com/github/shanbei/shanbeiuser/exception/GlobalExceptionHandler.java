package com.github.shanbei.shanbeiuser.exception;

import com.github.shanbei.shanbeiuser.common.BaseResponse;
import com.github.shanbei.shanbeiuser.common.ErrorCode;
import com.github.shanbei.shanbeiuser.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author zhengqingquan
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 这个方法只去捕获BussinessException异常。
     *
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse bussinessExceptionHandler(BusinessException e) {
        log.error("businessException" + e.getMessage(), e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtionExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }

}
