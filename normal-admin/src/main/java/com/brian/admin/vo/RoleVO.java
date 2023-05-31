package com.brian.admin.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian
 * @date 2023/5/25
 **/
@Data
public class RoleVO {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
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

    private List<Long> menuIds = new ArrayList<>();

    private static final long serialVersionUID = 1L;

}
