package com.brian.admin.controller;

import com.brian.admin.service.BlogService;
import com.brian.admin.vo.BlogVO;
import com.brian.common.core.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/25
 **/
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @RequestMapping("/list")
    public Result list(@RequestBody BlogVO blog) {

        return blogService.page(blog);

    }
}
