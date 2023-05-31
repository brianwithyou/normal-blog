package com.brian.search.server.controller;

import com.brian.common.core.Result;
import com.brian.search.server.service.SearchService;
import com.brian.search.server.vo.SearchVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/")
public class SearchController {

    @Resource
    private SearchService searchService;

    @RequestMapping("/search")
    public Result<?> search(@RequestBody SearchVO searchVo) {
//        Map<String, Object> res = searchService.search(new HashMap<>());
        String keyword = searchVo.getKeyword();
        Integer pageNum = searchVo.getPageNum();
        Integer pageSize = searchVo.getPageSize();
        Map<String, Object> res = searchService.search(keyword, pageNum, pageSize);
        return Result.success(res);
    }
    @RequestMapping("/initBlogs")
    public Result<?> initBlogs() throws IOException {

        searchService.initEsBlogs();
        return Result.success();
    }

    @RequestMapping("/list")
    public Result<?> list() {

        return searchService.list();
    }
    @RequestMapping("/delete")
    public Result<?> delete(@RequestBody Long id) throws IOException {
        return searchService.deleteById(id);
    }

    @RequestMapping("/deleteAll")
    public Result<?> deleteAll() throws IOException {
        return searchService.deleteAll();
    }


}
