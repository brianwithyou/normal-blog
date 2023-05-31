package com.brian.web.server.controller;

import com.brian.common.core.Result;
import com.brian.web.server.service.BlogService;
import com.brian.web.server.vo.BlogVO;
import com.brian.web.server.vo.UserVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
@RequestMapping("/rank")
public class RankController {

    @Resource
    private BlogService blogService;

    @ResponseBody
    @PostMapping("/blogs")
    public Result<?> hotBlogs() {
        List<BlogVO> ranks = blogService.totalCollectRank();
        Result<List<BlogVO>> success = Result.success(ranks);
        return success;
    }

    @ResponseBody
    @PostMapping("/pay")
    public Result<?> pay() {
        List<UserVO> ranks = blogService.totalPayRank();
        Result<List<UserVO>> success = Result.success(ranks);
        return success;
    }
}
