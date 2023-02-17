package com.newland.cloud.exception;


import com.newland.cloud.enumeration.ResultCode;
import com.newland.cloud.model.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author leell
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public RestResponse handleNotSupportException(Exception e) {
        String message = "不支持该请求类型";
        return RestResponse.error(ResultCode.ERROR.getCode(), message);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public RestResponse handleAccessDeniedException(AccessDeniedException e) {
        log.error(ResultCode.UNAUTHORIZED.getDesc() + "：", e);
        return RestResponse.error(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getDesc());
    }

    @ExceptionHandler({BusinessException.class})
    public RestResponse handleBusinessException(BusinessException e) {
        log.error(e.getMessage());
        return RestResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public RestResponse handleValidateException(MethodArgumentNotValidException e) {
        return RestResponse.error(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public RestResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息：", e);
        String message = "系统内部异常，异常信息";
        return RestResponse.error(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }
}
