package com.brian.common.core;

import com.brian.common.constant.exception.GlobalErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : brian
 * @since 0.1
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<String> handler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        ObjectError objectError = result.getAllErrors().stream().findFirst().get();
        String errorMsg = objectError.getDefaultMessage();
        log.error("校验异常：{}", errorMsg, e);
        return Result.fail(errorMsg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result<String> handler(IllegalArgumentException e) {
        String errorMsg = e.getMessage();
        log.error("参数异常：{}", errorMsg, e);
        return Result.fail(errorMsg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public Result<String> handler(Exception e) {
        String errorMsg = e.getMessage();
        log.error("运行时异常：{}", errorMsg, e);
        return Result.fail(GlobalErrorCodeEnum.INTERNAL_SERVER_ERROR);
    }
}
