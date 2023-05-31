//package com.brian.normal.common.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * @author : brian
// * @since 0.1
// */
//@Data
//@Component
//@ConfigurationProperties(prefix = "normal.jwt")
//public class JwtUtil {
//
//    private long expire;
//
//    private String secretKey;
//
//    private String header;
//
//    /**
//     * 生成token
//      * @param username username
//     * @return results
//     */
//    public String generateToken(String username) {
//        Date nowDate = new Date();
//        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);
//
//        return Jwts.builder()
//                .setHeaderParam("typ", "JWT")
//                .setSubject(username)
//                .setIssuedAt(nowDate)
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }
//
//    // 解析jwt
//
//    public Claims getClaimByToken(String jwt) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(jwt)
//                    .getBody();
//        } catch (Exception e) {
//            /*return new DefaultClaims().setSubject("18679974900")
//                    .setExpiration(DateUtil.offsetDay(new Date(), 8))
//                    ;*/
//            return null;
//        }
//    }
//
//    /**
//     *  验证是否过期
//     */
//    public boolean isTokenExpired(Claims claims) {
//        return claims.getExpiration().before(new Date());
//    }
//}
