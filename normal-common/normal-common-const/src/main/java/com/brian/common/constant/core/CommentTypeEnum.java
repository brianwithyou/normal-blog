package com.brian.common.constant.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Brian
 * @date 2023/5/21
 **/
@Getter
@AllArgsConstructor
public enum CommentTypeEnum {

    LEAVE_MESSAGE("leaveMessage", "留言"),

    REPLAY("replay", "回复"),
    COMMENT("comment", "博客评论"),

    ;
    private final String value;

    private final String comment;

}
