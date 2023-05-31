package com.brian.admin.service;

import com.brian.admin.vo.BlogVO;
import com.brian.common.core.Result;

public interface BlogService {
    Result page(BlogVO blog);
}
