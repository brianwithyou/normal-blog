package com.brian.web.server.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论表
 * @TableName t_comment
 */
@Data
public class CommentVO implements Serializable {

    private Long id;

    private Long userId;
    private String username;

    private String userAvatar;
    private String parentName;

    private Long parentId;

    private Long rootId;

    private Long toUserId;

    private String content;

    private String blogId;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private LocalDateTime createTime;

    private String source;

    /**
     * 评论类型 留言,评论,回复
     */
    private String type;

    List<CommentVO> children;
    private static final long serialVersionUID = 1L;

    private Integer commentNum;
}
