package com.brian.rbac.server.config;

import cn.hutool.core.util.StrUtil;
import com.brian.rbac.server.handler.AccountUser;
import com.brian.user.api.UserFeignClient;
import com.brian.user.api.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/***
 *
 * 自定义授权认证类
 * @author brian
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    ClientDetailsService clientDetailsService;

    @Resource
    private UserFeignClient userFeignClient;

    /****
     * 自定义授权认证
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        // 取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 登录一次会有两次请求，第一次 先验证client_id和client_secret
//        if (authentication == null) {
//            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
//            if (clientDetails != null) {
//                //秘钥
//                String clientSecret = clientDetails.getClientSecret();
//                //静态方式
////                return new User(username, bCryptPasswordEncoder.encode(clientSecret)
////                        , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//                // 数据库查找方式
//                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
//            }
//        }

        if (StrUtil.isEmpty(username)) {
            return null;
        }

        // 第二次请求，验证用户的账号密码
        // /oauth/token端点 ，oauth2.0根据 client_id client_secret username password生成token时
        // 会经过这里的逻辑，将数据库库有的用户名和密码 加入context中
        UserDTO userDto = userFeignClient.getUserByUsername(username);
        String pwd = userDto.getPassword();
        // 角色信息
        // 也可以在 WebSecurityConfig中 的AuthenticationManagerBuilder 设置
        // 在CustomUserAuthenticationConverter 中设置生效的是哪一个
        String roles = "user,operator";
//        String pwd = new BCryptPasswordEncoder().encode("normal");
        return new AccountUser(userDto.getId(), username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
    }
}
