package com.brian.user.server.convert;

import com.brian.user.api.dto.FollowerDTO;
import com.brian.user.server.entity.Follower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@Mapper
public interface FollowerConvert {

    FollowerConvert INSTANCE = Mappers.getMapper(FollowerConvert.class);

    FollowerDTO convertDto(Follower bean);

    List<FollowerDTO> convertDtoList(List<Follower> beans);

}
