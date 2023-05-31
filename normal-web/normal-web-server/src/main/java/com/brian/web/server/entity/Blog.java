package com.brian.web.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 博客表
 * @author brian
 */
@TableName(value ="t_blog")
@Data
public class Blog implements Serializable {
    /**
     * 唯一uid
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客简介
     */
    private String summary;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 标签uid
     */
    private String tagUid;

    /**
     * 博客点击数
     */
    private Long readCount;

    /**
     * 博客收藏数
     */
    private Long collectCount;

    /**
     * 标题图片uid
     */
    private String cover;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 管理员uid
     */
    private String adminUid;

    /**
     * 是否原创（0:不是 1：是）
     */
    private String isOriginal;

    /**
     * 作者
     */
    private String author;

    /**
     * 文章出处
     */
    private String articlesPart;

    /**
     * 博客分类UID
     */
    private String blogSortUid;

    /**
     * 推荐等级(0:正常)
     */
    private Integer level;

    /**
     * 是否发布：0：否，1：是
     */
    private String isPublish;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 是否开启评论(0:否 1:是)
     */
    private Integer openComment;

    /**
     * 类型【0 博客， 1：推广】
     */
    private Integer type;

    /**
     * 外链【如果是推广，那么将跳转到外链】
     */
    private String outsideLink;

    /**
     * 文章来源【0 后台添加，1 用户投稿】
     */
    private Integer articleSource;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}