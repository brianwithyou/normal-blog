package com.brian.search.server.mapper;

import com.brian.search.api.entity.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : brian
 * @since 0.1
 */
@Repository
public interface BlogRepository extends ElasticsearchRepository<EsBlog, Long> {


}
