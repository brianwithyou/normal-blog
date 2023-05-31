package com.brian.admin.service.impl;

import com.brian.admin.service.BlogService;
import com.brian.admin.vo.BlogVO;
import com.brian.common.core.Result;
import com.brian.web.api.BlogFeignClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Brian
 * @date 2023/5/25
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogFeignClient blogFeignClient;

    @Override
    public Result page(BlogVO blog) {
        blogFeignClient.page(blog.getTitle(), blog.getAuthorId(), blog.getPageNum(), blog.getPageSize());
        return null;
    }
}
