package com.brian.web.server.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brian.common.core.PageResult;
import com.brian.web.server.entity.Blog;
import com.brian.web.server.vo.BlogVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PageResultConvert {
    PageResultConvert INSTANCE = Mappers.getMapper(PageResultConvert.class);

    @Mappings({
            @Mapping(source = "current", target = "pageNum"),
            @Mapping(source = "size", target = "pageSize"),
            @Mapping(source = "records", target = "list"),
    })
    PageResult<BlogVO> convert(Page<Blog> bean);

}

