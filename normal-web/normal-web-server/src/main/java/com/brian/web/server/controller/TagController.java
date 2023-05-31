package com.brian.web.server.controller;

import com.brian.common.core.Result;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.entity.Tag;
import com.brian.web.server.service.TagService;
import com.brian.web.server.util.ContextUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @RequestMapping("/list")
    public Result<?> list() {

        List<Tag> tags = tagService.list();
        return Result.success(tags);
    }

    @RequestMapping("/create")
    public Result<?> create(@RequestBody Tag tag) {

        UserDTO currentUser = ContextUtil.getCurrentUser();

        tag.setUserId(currentUser.getId());

        return Result.success(tagService.save(tag));
    }



}
