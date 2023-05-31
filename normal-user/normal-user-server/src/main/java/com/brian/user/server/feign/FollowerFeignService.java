package com.brian.user.server.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.brian.user.api.FollowerFeignClient;
import com.brian.user.api.dto.FollowerDTO;
import com.brian.user.server.convert.FollowerConvert;
import com.brian.user.server.entity.Follower;
import com.brian.user.server.service.FollowerService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : brian
 * @since 0.1
 */
@RestController
public class FollowerFeignService implements FollowerFeignClient {

    @Resource
    private FollowerService followerService;

    @Override
    public List<FollowerDTO> listFollowers(Long userId) {
        List<Follower> followers = followerService.list(new QueryWrapper<Follower>().eq("user_id", userId));
        return FollowerConvert.INSTANCE.convertDtoList(followers);
    }
}
