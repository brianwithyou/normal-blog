package com.brian.admin.convert;

import com.brian.admin.entity.Tag;
import com.brian.admin.vo.TagVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    Tag convert(TagVO bean);

}
