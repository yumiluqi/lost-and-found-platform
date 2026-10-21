package com.example.lostfound.common;

import lombok.Data;

/**
 * 统一返回结果类
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result() {}

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 成功返回（带数据） */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /** 成功返回（不带数据） */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /** 成功返回（自定义消息+数据） */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    /** 失败返回（自定义错误码+消息） */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /** 失败返回（默认500） */
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }
}
