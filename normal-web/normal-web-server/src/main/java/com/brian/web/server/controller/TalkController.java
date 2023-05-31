package com.brian.web.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.core.PageParam;
import com.brian.common.core.Result;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.entity.Talk;
import com.brian.web.server.service.TalkService;
import com.brian.web.server.util.ContextUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Brian
 * @date 2023/5/21
 **/
@RestController
@RequestMapping("/talk")
public class TalkController {

    @Resource
    private TalkService talkService;

    @RequestMapping("/list")
    public Result<?> list(@RequestBody PageParam pageParam) {

        Page<Talk> page = new Page<>();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());
        Page<Talk> talkPage = talkService.page(page, new QueryWrapper<Talk>()
                .ne("status", 0)
                .orderByDesc("create_time"));

        return Result.success(talkPage);
    }

    @RequestMapping("/create")
    public Result<?> create(@RequestBody Talk talk) {

        LocalDateTime now = LocalDateTime.now();
        UserDTO curUser = ContextUtil.getCurrentUser();

        talk.setAuthorId(curUser.getId());
        talk.setAuthorName(curUser.getUsername());
        talk.setCreateTime(now);
        talk.setUpdateTime(now);

        return Result.success();
    }

    @RequestMapping("/update")
    private Result<?> update(@RequestBody Talk talk) {

        LocalDateTime now = LocalDateTime.now();
        UserDTO curUser = ContextUtil.getCurrentUser();
        if (!Objects.equals(talk.getAuthorId(), curUser.getId())) {
            Result.fail("只能修改自己的说说");
        }

        talkService.updateById(talk);
        return Result.success();
    }

    @RequestMapping("/delete")
    public Result<?> delete(@RequestBody Talk talk) {
        Talk entity = new Talk();
        entity.setStatus(0);
        entity.setId(talk.getId());
        talkService.updateById(entity);
        return Result.success();
    }
}
