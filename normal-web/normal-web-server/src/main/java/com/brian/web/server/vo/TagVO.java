package com.brian.web.server.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Brian
 * @date 2023/5/30
 **/
@Data
public class TagVO {

    private Long id;

    private String name;

    private Long userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer status;

    /**
     * 标签数量
     */
    private Long count;

    private static final long serialVersionUID = 1L;

}
