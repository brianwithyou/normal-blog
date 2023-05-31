package com.brian.search.server.service;

import com.brian.common.core.Result;
import com.brian.web.api.dto.BlogDTO;

import java.io.IOException;
import java.util.Map;

/**
 * @author : Brian
 * @since 0.1
 */
public interface SearchService {

    void initEsBlogs() throws IOException;

    Result<?> list();

    Result<?> deleteById(Long id) throws IOException;

    Map<String, Object> search(Map<String, String> param);

    Map<String, Object> search(String keyword, Integer pageNum, Integer pageSize);

    BlogDTO insertById(long blogId) throws IOException;

    Result<?> deleteAll() throws IOException;
}
