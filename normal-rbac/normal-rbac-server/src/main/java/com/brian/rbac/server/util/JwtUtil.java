package com.brian.rbac.server.util;

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : brian
 * @since 0.1
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "normal.jwt")
public class JwtUtil {

    private long expire;

    private String secretKey;

    private String header;

    /**
     * 生成管理员token
     * @return token
     */
    public static String generateAdminToken() {

        // 加载证书
        ClassPathResource resource = new ClassPathResource("normal.jks");
        KeyStoreKeyFactory keyStoreFactory = new KeyStoreKeyFactory(resource, "normal".toCharArray());
        KeyPair keyPair = keyStoreFactory.getKeyPair("normal", "normal".toCharArray());

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> payload = new HashMap<>();
        payload.put("rbac-server", "brian");

        // 调用其他资源服务时，默认给认证服务器加一个 admin的 token
        payload.put("authorities", new String[]{"admin","oauth"});

        Jwt jwt = JwtHelper.encode(JSONUtil.toJsonStr(payload), new RsaSigner(privateKey));
        return jwt.getEncoded();
    }

    /**
     * 生成token
      * @param account account
     * @return results
     */ 
    public String generateToken(String account) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(account)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // 解析jwt
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  验证是否过期
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
