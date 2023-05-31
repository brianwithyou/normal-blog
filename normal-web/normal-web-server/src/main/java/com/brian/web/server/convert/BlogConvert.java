package com.brian.web.server.convert;

import com.brian.web.api.dto.BlogDTO;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.vo.BlogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface BlogConvert {

    BlogConvert INSTANCE = Mappers.getMapper(BlogConvert.class);
    
    Blog convert(BlogVO blog);

    List<BlogVO> convertVO(List<Blog> blogs);

    BlogVO convertVO(Blog blog);

    List<BlogDTO> convertDto(List<Blog> list);
    BlogDTO convertDto(Blog bean);
}
