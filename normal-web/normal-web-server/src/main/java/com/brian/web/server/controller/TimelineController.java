package com.brian.web.server.controller;

import com.brian.common.core.PageParam;
import com.brian.common.core.PageResult;
import com.brian.common.core.Result;
import com.brian.web.server.service.TimelineService;
import com.brian.web.server.vo.BlogVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/30
 **/
@RestController
@RequestMapping("/timeline")
public class TimelineController {

    @Resource
    private TimelineService timelineService;

    @RequestMapping("/list")
    public Result<PageResult<BlogVO>> list(PageParam pageParam) {

        return timelineService.list(pageParam);
    }
}
