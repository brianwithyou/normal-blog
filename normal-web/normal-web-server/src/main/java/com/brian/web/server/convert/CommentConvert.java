package com.brian.web.server.convert;

import com.brian.web.server.entity.Comment;
import com.brian.web.server.vo.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface CommentConvert {

    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);
    
    CommentVO convertVo(Comment bean);

    List<CommentVO> convertVo(List<Comment> records);
}
