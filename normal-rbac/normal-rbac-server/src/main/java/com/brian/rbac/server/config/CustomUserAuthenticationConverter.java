package com.brian.rbac.server.config;

import com.brian.rbac.server.handler.AccountUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author brian
 */
@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Resource
    UserDetailsService userDetailsService;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        String name = authentication.getName();
        response.put("username", name);

        Object principal = authentication.getPrincipal();
        AccountUser userJwt;
        if(principal instanceof AccountUser){
            userJwt = (AccountUser) principal;
        }else{
            //refresh_token默认不去调用userDetailService获取用户信息，这里我们手动去调用，得到 UserJwt
            UserDetails userDetails = userDetailsService.loadUserByUsername(name);
            userJwt = (AccountUser) userDetails;
        }
        response.put("id", userJwt.getId());
        response.put("name", userJwt.getUsername());
        response.put("company", "brian");
        response.put("address", "beijing");
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            // 此处的权限是 WebSecurityConfig中设置的，自动拼接了 ROLE_
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 此处的权限是 UserDetailsServiceImpl 设置的，可以从db动态获取
            Collection<GrantedAuthority> authorities = userJwt.getAuthorities();
            response.put("authorities", AuthorityUtils.authorityListToSet(authorities));
        }
        return response;
    }

}
