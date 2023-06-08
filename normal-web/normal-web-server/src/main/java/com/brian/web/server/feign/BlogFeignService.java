package com.brian.web.server.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.core.PageResult;
import com.brian.web.api.BlogFeignClient;
import com.brian.web.api.dto.BlogDTO;
import com.brian.web.server.convert.BlogConvert;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.service.BlogService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BlogFeignService implements BlogFeignClient {

    @Resource
    private BlogService blogService;
    @Override
    public List<BlogDTO> list() {

        List<Blog> list = blogService.list(new QueryWrapper<Blog>().ne("status", 0));
        return BlogConvert.INSTANCE.convertDto(list);
    }
    @Override
    public BlogDTO getById(Long id) {
        Blog blog = blogService.getById(id);
        return BlogConvert.INSTANCE.convertDto(blog);
    }

    @Override
    public PageResult<BlogDTO> page(String title, Long authorId, Integer pageNum, Integer pageSize) {
        Page<Blog> page = new Page<>(pageNum, pageSize);

        Page<Blog> pageData = blogService.page(page, new QueryWrapper<Blog>()
                .like(title != null, "title", title).eq("uid", authorId));


        return null;
    }
}
