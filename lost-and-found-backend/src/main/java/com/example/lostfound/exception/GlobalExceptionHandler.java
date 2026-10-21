package com.example.lostfound.exception;

import com.example.lostfound.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 捕获Controller抛出的所有异常，统一返回Result格式
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 处理所有未捕获的异常 */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        return Result.error(500, "服务器内部错误：" + e.getMessage());
    }

    /** 处理参数异常 */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgument(IllegalArgumentException e) {
        return Result.error(400, "参数错误：" + e.getMessage());
    }
}
