package com.brian.web.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brian.common.core.Result;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.entity.Collect;
import com.brian.web.server.service.CollectService;
import com.brian.web.server.util.ContextUtil;
import com.brian.web.server.vo.BlogVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/collect")
public class CollectController {

    @Resource
    private CollectService collectService;
    @RequestMapping("/isCollected")
    public Result<?> isCollected(@RequestBody BlogVO blog) {
        Long blogId = blog.getId();
        UserDTO currentUser = ContextUtil.getCurrentUser();
        Collect collect = collectService.getOne(new QueryWrapper<Collect>()
                .eq("user_id", currentUser.getId())
                .eq("blog_id", blogId)
        );
        boolean collected = collect != null && collect.getStatus() == 1;
        return Result.success(collected);
    }
}
