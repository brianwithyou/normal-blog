package com.brian.web.server.service;

import com.brian.common.core.PageParam;
import com.brian.common.core.PageResult;
import com.brian.common.core.Result;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.vo.BlogVO;

public interface TimelineService {
    Result<PageResult<BlogVO>> list(PageParam pageParam);
}
