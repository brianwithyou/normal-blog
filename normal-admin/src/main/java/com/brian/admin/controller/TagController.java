package com.brian.admin.controller;

import com.brian.admin.service.TagService;
import com.brian.admin.vo.TagVO;
import com.brian.common.core.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/28
 **/
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private TagService tagService;

    @RequestMapping("/list")
    public Result list(@RequestBody TagVO tag) {
        return Result.success(tagService.list());
    }

    @RequestMapping("/create")
    public Result create(@RequestBody TagVO tag) {
        return Result.success(tagService.create(tag));
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody TagVO tag) {
        return Result.success(tagService.delete(tag));
    }

}
