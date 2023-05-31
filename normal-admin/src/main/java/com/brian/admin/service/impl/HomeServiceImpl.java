package com.brian.admin.service.impl;

import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.brian.admin.service.HomeService;
import com.brian.admin.util.IpUtil;
import com.brian.admin.vo.HomeDataVO;
import com.brian.common.constant.Const;
import com.brian.common.core.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.brian.common.constant.Const.*;

/**
 * @author Brian
 * @date 2023/5/26
 **/
@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public Result<HomeDataVO> getData() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> asyncViewCount = CompletableFuture.supplyAsync(() -> {
            Object count = redisTemplate.opsForValue().get(BLOG_VIEWS_COUNT);
            return Integer.parseInt(Optional.ofNullable(count).orElse(0).toString());
        });
        HomeDataVO res = new HomeDataVO();
        res.setViewCount(Long.valueOf(asyncViewCount.get()));
        return Result.success(res);
    }

    @Override
    public Result<?> report(HttpServletRequest request) {

        String ipAddress = IpUtil.getIpAddress(request);
        UserAgent userAgent = IpUtil.getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        OS operatingSystem = userAgent.getOs();
        String uuid = ipAddress + browser.getName() + operatingSystem.getName();
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        if (Boolean.FALSE.equals(redisTemplate.opsForSet().isMember(Const.NORMAL_VISITORS, md5))) {
            String ipSource = IpUtil.getIpSource(ipAddress);
            if (StringUtils.isNotBlank(ipSource)) {
                String ipProvince = IpUtil.getIpProvince(ipSource);
                redisTemplate.opsForHash().increment(VISITOR_AREA, ipProvince, 1L);
            } else {
                redisTemplate.opsForHash().increment(VISITOR_AREA, UNKNOWN, 1L);
            }
            redisTemplate.opsForValue().increment(BLOG_VIEWS_COUNT, 1);
            redisTemplate.opsForSet().add(UNIQUE_VISITOR, md5);
        }
        return Result.success();
    }
}
