package com.brian.common.constant.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : brian
 * @since 0.1
 */
@Getter
@AllArgsConstructor
public enum GlobalErrorCodeEnum {

    /**
     * 错误信息
     */
    SUCCESS(200, "操作成功"),

    BAD_REQUEST(400, "请求参数不正确"),
    UNAUTHORIZED(401, "账号未登录"),
    FORBIDDEN(403, "没有该操作权限"),
    NOT_FOUND(404, "请求未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不正确"),
    LOCKED(423, "请求失败，请稍后重试"),
    TOO_MANY_REQUESTS(429, "请求过于频繁，请稍后重试"),

    INTERNAL_SERVER_ERROR(500, "系统异常"),

    REPEATED_REQUESTS(900, "重复请求，请稍后重试"),
    DEMO_DENY(901, "演示模式，禁止写操作"),
    UNKNOWN(999, "未知错误"),

    /**
     * 400  模块名 001 web  002 user 003 admin 004 rbac
     */
    BLOG_NOT_FOUND(400001001, "文章未找到"),

    OPERATION_FAILED(500003001, "操作失败"),
    REPEATED_USER(400003002, "用户已存在"),
    INVALID_PASSWORD(400004001, "账号或密码不正确"),
    ;

    private final Integer code;

    private final String msg;

}
