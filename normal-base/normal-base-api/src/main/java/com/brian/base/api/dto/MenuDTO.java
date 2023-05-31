package com.brian.base.api.dto;

import lombok.Data;

/**
 * @author : brian
 * @since 0.1
 */
@Data
public class MenuDTO {

    private Long id;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    private String name;

    /**
     * 菜单URL
     */
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    private String component;
}
