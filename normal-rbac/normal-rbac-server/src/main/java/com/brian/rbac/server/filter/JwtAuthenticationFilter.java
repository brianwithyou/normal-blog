//package com.brian.rbac.server.filter;
//
//import cn.hutool.core.util.StrUtil;
//import com.brian.rbac.server.util.JwtUtil;
//import com.brian.user.api.UserFeignClient;
//import com.brian.user.api.dto.UserDTO;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.annotation.Resource;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//
///**
// * @author : brian
// * @since 0.1
// */
//public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
//
//    @Resource
//    private JwtUtil jwtUtil;
//
//    @Resource
//    private UserFeignClient userFeignClient;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        String jwt;
//        // jwt为空，继续执行过滤器链，例如访问可以匿名访问的资源
//        if (StrUtil.isBlank(jwt = request.getHeader(jwtUtil.getHeader()))){
//            chain.doFilter(request, response);
//            return;
//        }
//
//        Claims claim = jwtUtil.getClaimByToken(jwt);
//        if (claim == null) {
//            throw new JwtException("token无效");
//        }
//        if (jwtUtil.isTokenExpired(claim)) {
//            throw new JwtException("token已经过期");
//        }
//
//        String account = claim.getSubject();
//        UserDTO user = userFeignClient.getUserByAccount(account);
//
//        if( user != null ) {
//            String password = user.getPassword();
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password, Collections.emptyList());
//            SecurityContextHolder.getContext().setAuthentication(token);
//        }
//        chain.doFilter(request, response);
//    }
//}
