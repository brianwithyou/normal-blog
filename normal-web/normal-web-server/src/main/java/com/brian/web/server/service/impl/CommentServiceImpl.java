package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brian.common.core.Result;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.convert.CommentConvert;
import com.brian.web.server.entity.Comment;
import com.brian.web.server.mapper.CommentMapper;
import com.brian.web.server.service.CommentService;
import com.brian.web.server.vo.CommentListVO;
import com.brian.web.server.vo.CommentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.brian.common.constant.core.CommentTypeEnum.LEAVE_MESSAGE;

/**
 * @author Brian
 * @description 针对表【t_comment(评论表)】的数据库操作Service实现
 * @createDate 2023-05-21 21:33:59
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public Result<?> list(CommentListVO comment) {
        if (comment.getBlogId() != null) {
            List<Comment> comments = list(new QueryWrapper<Comment>().eq("blog_id", comment.getBlogId()));
            return Result.success(comments);
        }

        if (comment.getRootId() != null) {
        }
        Page<Comment> page = new Page<>(comment.getPageNum(), comment.getPageSize());

        if (LEAVE_MESSAGE.getValue().equals(comment.getType())) {
            Page<Comment> commentPage = page(page, new QueryWrapper<Comment>()
                    .eq("type", LEAVE_MESSAGE.getValue())
                    .orderByDesc("create_time"));

            List<Long> userIds = new ArrayList<>();
            commentPage.getRecords().forEach(item -> {
                        userIds.add(item.getUserId());
                    }
            );
            List<UserDTO> users = userFeignClient.listByIds(userIds);
            Map<Long, UserDTO> userMap = new HashMap<>();
            users.forEach(item -> {
                userMap.put(item.getId(), item);
            });

            List<CommentVO> commentVOS = CommentConvert.INSTANCE.convertVo(commentPage.getRecords());

            commentVOS.forEach(item -> {
                item.setUsername(userMap.get(item.getUserId()).getUsername());
                item.setUserAvatar(userMap.get(item.getUserId()).getAvatar());
                item.setChildren(listCommentsByRootId(item.getId(), userMap));
                item.setCommentNum(item.getChildren().size());
            });

            return Result.success(commentVOS);
        }
        return Result.fail("查询评论失败");
    }

    public List<CommentVO> listCommentsByRootId(Long rootId, Map<Long, UserDTO> userMap) {
        List<Comment> comments = list(new QueryWrapper<Comment>()
                .eq("root_id", rootId).ne("status", 0).orderByAsc("create_time"));
        List<CommentVO> commentVOS = CommentConvert.INSTANCE.convertVo(comments);
        commentVOS.forEach(item -> {
            item.setUsername(userMap.get(item.getUserId()).getUsername());
            item.setUserAvatar(userMap.get(item.getUserId()).getAvatar());
            item.setParentName(userMap.get(item.getToUserId()).getUsername());

        });
        return commentVOS;
    }
}




