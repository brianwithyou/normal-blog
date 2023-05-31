package com.brian.web.server.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brian.common.core.Result;
import com.brian.common.core.ScrollResult;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.service.BlogService;
import com.brian.web.server.vo.BlogVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;

    @GetMapping("/info")
    public Result<?> getBlog(@RequestParam("id") Long id) {
        return blogService.getBlog(id);
    }

    @PostMapping("/create")
    public Result<?> create(@RequestBody BlogVO blog) {
        if (StrUtil.isNotBlank(blog.getSummary()) && blog.getSummary().length() >= 200) {
            return Result.fail("摘要内容超出长度限制");
        }
        boolean success = blogService.create(blog);
        return Result.success(success);
    }
    @PostMapping("/delete")
    public Result<?> delete(@RequestBody BlogVO blog) {
        return blogService.delete(blog);
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody BlogVO blog) {
        return blogService.saveBlog(blog);
    }

    @PostMapping("/list")
    @ResponseBody
    public Result<IPage<Blog>> list(@RequestBody BlogVO blog) {
        return blogService.list(blog);
    }
    @PostMapping("/feed")
    @ResponseBody
    public Result<ScrollResult<BlogVO>> feed(@RequestParam("lastId") Long max
            , @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return blogService.feed(max, offset);
    }

    @GetMapping("/collect")
    public Result<?> collect(@RequestParam Long id, @RequestParam Integer flag) {
        return Result.success(blogService.collect(id, flag));
    }

}
