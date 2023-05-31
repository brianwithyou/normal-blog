package com.brian.common.core;

import com.brian.common.constant.exception.GlobalErrorCodeEnum;
import com.brian.normal.common.util.ContextUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.brian.common.constant.exception.GlobalErrorCodeEnum.INTERNAL_SERVER_ERROR;
import static com.brian.common.constant.exception.GlobalErrorCodeEnum.SUCCESS;

/**
 * @author : brian
 * @since 0.1
 */
@Data
// 不加构造方法无法在feign调用传输
@NoArgsConstructor
public class Result<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    private String requestId;

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.requestId = ContextUtil.requestId();
    }

    private Result(GlobalErrorCodeEnum errorCode, T data) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.data = data;
        this.requestId = ContextUtil.requestId();
    }

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(INTERNAL_SERVER_ERROR, null);
    }
    public static <T> Result<T> fail(GlobalErrorCodeEnum errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), null);
    }

    public static <T> Result<T> fail(GlobalErrorCodeEnum errorCode, T data) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg(), data);
    }
    public static <T> Result<T> fail(String msg) {
        return new Result<>(INTERNAL_SERVER_ERROR.getCode(), msg, null);
    }
}