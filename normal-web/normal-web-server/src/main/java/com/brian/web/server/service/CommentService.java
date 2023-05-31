package com.brian.web.server.service;

import com.brian.common.core.Result;
import com.brian.web.server.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.brian.web.server.vo.CommentListVO;

/**
* @author Brian
* @description 针对表【t_comment(评论表)】的数据库操作Service
* @createDate 2023-05-21 21:33:59
*/
public interface CommentService extends IService<Comment> {

    Result<?> list(CommentListVO comment);
}
