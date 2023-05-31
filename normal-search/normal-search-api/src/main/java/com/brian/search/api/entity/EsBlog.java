package com.brian.search.api.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @author : brian
 * @since 0.1
 */
@Data
@Document(indexName="blog")
public class EsBlog {

    @Id
    private Long id;

    private Long uid;

    @Field(type = FieldType.Text)
    private String title;

    private String summary;

    private String content;

    @Field(type = FieldType.Keyword)
    private String category;

    private String tagId;

    /**
     * 博客点击数
     */
    private Integer readCount;

    /**
     * 博客收藏数
     */
    private Integer collectCount;

    /**
     * 标题图片uid
     */
    private String fileId;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String adminUid;

    private String isOriginal;

    private String author;

    private String articlesPart;

    private String blogSortUid;

    private Integer level;

    private String isPublish;
    private Integer sort;

    private Integer openComment;

    private Integer type;

    private String outsideLink;

    private String userId;

    private Integer articleSource;
}
