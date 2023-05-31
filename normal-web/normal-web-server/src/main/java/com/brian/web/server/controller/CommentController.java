package com.brian.web.server.controller;

import cn.hutool.core.util.StrUtil;
import com.brian.common.core.Result;
import com.brian.web.server.entity.Comment;
import com.brian.web.server.service.CommentService;
import com.brian.web.server.vo.CommentListVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.brian.common.constant.core.CommentTypeEnum.LEAVE_MESSAGE;
import static com.brian.web.server.util.ContextUtil.getCurrentUser;

/**
 * @author Brian
 * @date 2023/5/21
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @RequestMapping("/create")
    public Result<?> create(@RequestBody Comment comment) {

        LocalDateTime now = LocalDateTime.now();

        if (StrUtil.isBlank(comment.getContent())) {
            return Result.fail("评论不能为空");
        }
        if (comment.getToUserId() == null) {
            comment.setType(LEAVE_MESSAGE.getValue());
        }
        comment.setUserId(getCurrentUser().getId());
        comment.setUpdateTime(now);
        comment.setCreateTime(now);
        commentService.save(comment);

        return Result.success("添加成功");
    }

    @RequestMapping("/delete")
    public Result<?> delete(Comment comment) {

        Comment entity = new Comment();
        entity.setId(comment.getId());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setStatus(0);

        commentService.updateById(entity);
        return Result.success();

    }

    @RequestMapping("/update")
    public Result<?> update(Comment comment) {
        Comment byId = commentService.getById(comment.getId());
        if (!Objects.equals(getCurrentUser().getId(), byId.getUserId())) {
            return Result.fail("不能修改他人评论");
        }
        comment.setUpdateTime(LocalDateTime.now());
        commentService.updateById(comment);
        return Result.success("更新评论成功");
    }

    @RequestMapping("/list")
    public Result<?> list(@RequestBody CommentListVO comment) {

        return commentService.list(comment);

    }


}
