package com.brian.user.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@Data
public class RoleDTO {
    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String code;

    /**
     * 备注
     */
    private String remark;

    /**
     *
     */
    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime updateTime;

    /**
     *
     */
    private Integer status;
    private String description;
}
