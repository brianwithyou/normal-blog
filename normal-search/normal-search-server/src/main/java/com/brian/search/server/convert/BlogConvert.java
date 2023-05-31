package com.brian.search.server.convert;

import com.brian.search.api.entity.EsBlog;
import com.brian.web.api.dto.BlogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : Brian
 * @since 0.1
 */
@Mapper
public interface BlogConvert {

    BlogConvert INSTANCE = Mappers.getMapper(BlogConvert.class);

    EsBlog convert(BlogDTO bean);

    List<EsBlog> convert(List<BlogDTO> bean);
}
