package com.brian.admin.controller;

import com.brian.admin.entity.Category;
import com.brian.admin.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping("/list")
    public Result list(@RequestBody Category body) {
        return Result.success(categoryService.list());
    }

    @RequestMapping("/create")
    public Result create(@RequestBody Category body) {
        return Result.success(categoryService.create(body));
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody Category body) {
        return Result.success(categoryService.delete(body));
    }

}
