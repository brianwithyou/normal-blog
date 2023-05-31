//package com.brian.rbac.server.feign;
//
//import cn.hutool.json.JSONUtil;
//import com.brian.common.constant.Const;
//import com.brian.normal.common.util.RedisUtil;
//import com.brian.rbac.api.RbacAuthFeignClient;
//import com.brian.rbac.api.dto.UserDTO;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author : brian
// * @since 0.1
// */
//@RestController
//public class RbacAuthFeignService implements RbacAuthFeignClient {
//
//    @Resource
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Resource
//    private HttpServletRequest httpServletRequest;
//
//    @Resource
//    private RedisUtil redisUtil;
//
//    @Override
//    public String encodePwd(String password) {
//        return passwordEncoder.encode(password);
//    }
//
//    @Override
//    public UserDTO getCurrentUser() {
//        String token = httpServletRequest.getHeader(Const.AUTHORIZATION);
//        Object userObj = redisUtil.get(Const.REDIS_TOKEN_PREFIX + token);
//        String s = JSONUtil.toJsonStr(userObj);
//        return JSONUtil.toBean(s, UserDTO.class);
//    }
//}
