package com.brian.common.constant.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : brian
 * @since 0.1
 */
@Getter
@AllArgsConstructor
public enum AuthTypeEnum {

    /**
     * 鉴权类型
     */
    WEB("web", "web系统"),
    ADMIN("admin", "admin系统"),
    ;
    
    private String value;

    private String name;
}
