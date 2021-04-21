package com.aruoxi.ebookshop.common;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@ApiResponse(description = "通用返回格式")
public class CommonResult<T> {
    private Integer code;

    private String msg;

    private T data;

    public static <T> CommonResult<T> success() {
        return success("success", null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success( "success", data);
    }

    public static <T> CommonResult<T> success(String msg, T data) {
        return new CommonResult<T>(200, msg, data);
    }

    public static <T> CommonResult<T> success(Integer code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }

    public static <T> CommonResult<T> success(HttpStatus status, String msg, T data) {
        return new CommonResult<T>(status.value(), msg, data);
    }

    public static <T> CommonResult<T> fail(Integer code, String msg) {
        return new CommonResult<T>(code, msg, null);
    }

    public static <T> CommonResult<T> fail(HttpStatus status, String msg) {
        return new CommonResult<T>(status.value(), msg, null);
    }

    public static <T> CommonResult<T> fail(Integer code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }

    public CommonResult() {
    }

    public CommonResult(HttpStatus status, T data) {
        this.code = status.value();
        this.data = data;
    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
