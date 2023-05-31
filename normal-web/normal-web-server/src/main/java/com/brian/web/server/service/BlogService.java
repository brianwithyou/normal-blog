package com.brian.web.server.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.brian.common.core.Result;
import com.brian.common.core.ScrollResult;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.vo.BlogVO;
import com.brian.web.server.vo.UserVO;

import java.util.List;

/**
 * @author brian
 */
public interface BlogService extends IService<Blog> {

    Result<?> saveBlog(BlogVO blog);

    /**
     * 滚动分页接口
     *
     * @param max    lastId
     * @param offset 偏移数量
     * @return res
     */
    Result<ScrollResult<BlogVO>> feed(Long max, Integer offset);

    Result<IPage<Blog>> list(BlogVO blog);

    Result<?> getBlog(Long id);

    Result<?> read(Long id);

    Result<?> delete(BlogVO blog);

    boolean create(BlogVO blog);

    List<BlogVO> dailyHotBlogs();

    List<BlogVO> totalCollectRank();

    boolean collect(Long blogId, Integer flag);

    List<UserVO> totalPayRank();
}
