//package com.brian.rbac.server.handler;
//
//import com.brian.user.api.UserFeignClient;
//import com.brian.user.api.dto.UserDTO;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///**
// * spring security拦截/login接口后，通过UserDetailsService的loadUserByUsername加载用户鉴权
// * @author : brian
// * @since 0.1
// */
//@Data
////@Service
//public class UserDetailServiceImpl implements UserDetailsService {
//
//    private String username;
//
//    private String password;
//
//    private String company;
//
//    private List<GrantedAuthority> authorities;
//
//    @Resource
//    private UserFeignClient userFeignClient;
//
//    @Override
//    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//
//        UserDTO user = userFeignClient.getUserByAccount(account);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户名或密码不正确");
//        }
//        return new AccountUser(user.getId(), account, user.getPassword(), getUserAuthorities(user.getId()));
//    }
//
//    /**
//     * 客户端id normal-blog
//     * 客户端密码 normal@123
//     *
//     * 普通用户 账号 任意账号， 密码： normal
//     */
//    public Collection<GrantedAuthority> getUserAuthorities(Long userId) {
////        String authorities = userService.getAuthorities(userId);
////        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//        List<GrantedAuthority> list = new ArrayList<>();
////        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        String permissions = "goods_list,seckill_list";
//        AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
//        return list;
//    }
//}
