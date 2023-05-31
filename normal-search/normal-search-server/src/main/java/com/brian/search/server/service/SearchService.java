package com.brian.search.server.service;

import java.io.IOException;
import java.util.Map;

/**
 * @author : Brian
 * @since 0.1
 */
public interface SearchService {

    void initEsBlogs() throws IOException;

    Map<String, Object> search(Map<String, String> param);

    Map<String, Object> search(String keyword, Integer pageNum, Integer pageSize);
}
