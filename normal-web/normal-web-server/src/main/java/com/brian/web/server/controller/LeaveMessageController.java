package com.brian.web.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.core.PageParam;
import com.brian.common.core.Result;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.entity.LeaveMessage;
import com.brian.web.server.service.LeaveMessageService;
import com.brian.web.server.util.ContextUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Brian
 * @date 2023/5/21
 **/
@RestController
@RequestMapping("/leaveMessage")
public class LeaveMessageController {
    @Resource
    private LeaveMessageService leaveMessageService;

    @RequestMapping("/list")
    public Result<?> list(@RequestBody PageParam pageParam) {

        Page<LeaveMessage> page = new Page<>();
        page.setCurrent(pageParam.getPageNum());
        page.setSize(pageParam.getPageSize());
        Page<LeaveMessage> leaveMessagePage = leaveMessageService.page(page,
                new QueryWrapper<LeaveMessage>()
                .ne("status", 0)
                .orderByDesc("create_time"));

        return Result.success(leaveMessagePage);
    }

    @RequestMapping("/create")
    public Result<?> create(@RequestBody LeaveMessage leaveMessage) {

        LocalDateTime now = LocalDateTime.now();
        UserDTO curUser = ContextUtil.getCurrentUser();

        leaveMessage.setAuthorId(curUser.getId());
        leaveMessage.setAuthorName(curUser.getUsername());
        leaveMessage.setCreateTime(now);
        leaveMessage.setUpdateTime(now);
        leaveMessageService.save(leaveMessage);

        return Result.success();
    }

    @RequestMapping("/update")
    private Result<?> update(@RequestBody LeaveMessage leaveMessage) {

        LocalDateTime now = LocalDateTime.now();
        UserDTO curUser = ContextUtil.getCurrentUser();
        if (!Objects.equals(leaveMessage.getAuthorId(), curUser.getId())) {
            Result.fail("只能修改自己的说说");
        }

        leaveMessageService.updateById(leaveMessage);
        return Result.success("修改成功");
    }

    @RequestMapping("/delete")
    public Result<?> delete(@RequestBody LeaveMessage leaveMessage) {
        LeaveMessage entity = new LeaveMessage();
        entity.setStatus(0);
        entity.setId(leaveMessage.getId());
        leaveMessageService.updateById(entity);
        return Result.success();
    }
}
