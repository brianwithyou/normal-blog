package com.brian.web.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@Data
public class MenuDTO {
    /**
     * 自增主键
     */
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer navbarLevel;

    /**
     *
     */
    private String summary;

    /**
     *
     */
    private Long parentId;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private String icon;

    /**
     *
     */
    private Integer isShow;

    /**
     *
     */
    private Integer isJumpExternalUrl;

    /**
     *
     */
    private Integer sort;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}
