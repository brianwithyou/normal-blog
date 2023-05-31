package com.brian.web.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.core.PageParam;
import com.brian.common.core.PageResult;
import com.brian.common.core.Result;
import com.brian.user.api.dto.UserDTO;
import com.brian.web.server.convert.PageResultConvert;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.service.BlogService;
import com.brian.web.server.service.TimelineService;
import com.brian.web.server.util.ContextUtil;
import com.brian.web.server.vo.BlogVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/30
 **/
@Service
public class TimelineServiceImpl implements TimelineService {

    @Resource
    private BlogService blogService;

    @Override
    public Result<PageResult<BlogVO>> list(PageParam pageParam) {

        UserDTO currentUser = ContextUtil.getCurrentUser();

        Page<Blog> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        Page<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>()
                .eq("uid", currentUser.getId())
                .ne("status", 0)
                .orderByDesc("create_time")
        );

        PageResult<BlogVO> convert = PageResultConvert.INSTANCE.convert(pageData);

        return Result.success(convert);
    }
}
