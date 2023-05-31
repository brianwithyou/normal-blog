package com.brian.rbac.server;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : brian
 * @since 0.1
 */
public class CreateJwtTest {


    /**
     * 解密证书，并从证书中获取 私钥
     *
     * 注意 私钥用来生成签名，公钥用来解析签名，获取数据
     */
    @Test
    public void createToken() {
        // 加载证书
        ClassPathResource resource = new ClassPathResource("normal.jks");

        KeyStoreKeyFactory keyStoreFactory = new KeyStoreKeyFactory(resource, "normal".toCharArray());
        KeyPair keyPair = keyStoreFactory.getKeyPair("normal", "normal".toCharArray());

        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> payload = new HashMap<>();
        payload.put("nickname", "brian");
        payload.put("address", "beijing");
        payload.put("role", "admin,user");

        Jwt jwt = JwtHelper.encode(JSONUtil.toJsonStr(payload), new RsaSigner(privateKey));
        String token = jwt.getEncoded();
        System.out.println(token);
    }

    @Test
    public void parseToken() {
//        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoienMiLCJyb2xlIjoiYWRtaW4sdXNlciIsIm5pY2tuYW1lIjoidG9tY2F0In0.gkwxKYe3CdtEbbHHgEJEf2AaRAyBlyQkqABerG5c85VTNQosenDe93ePTSRnjqI_6po8zbP26auO6lrj3fmsBwminDxB2kvcaTCkIDVjlnJMjjXZijdOeiy_TXnXeXma0VPGPFoxBnWKtubX41U0q3cbcjZ9J9Jmc-FCSUCT4S4yMOsFPd9zppefselJiR0OHvLsOhjthMRU6gpWfNR8fFvySN_SrbZM0T3GMaffpnD5UDRJ5ff1-eBPM30cmiPwIZkAnoJgBPqTtUDUn8VSNjayPbkURyq6-HE4vLVA2zHWhs7RY4DOBZY7allFDaFgwG6W7HS5tL39pf81U7NaIQ";
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZGRyZXNzIjoiYmVpamluZyIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjoibm9ybWFsIiwiY29tcGFueSI6ImJyaWFuIiwiaWQiOjE5LCJleHAiOjIxMTcwMjE3MjQsImF1dGhvcml0aWVzIjpbInVzZXIiLCJvcGVyYXRvciJdLCJqdGkiOiI3V05TWEFLUmotMzZnVkgzOUNZRndIYzRhU1UiLCJjbGllbnRfaWQiOiJub3JtYWwiLCJ1c2VybmFtZSI6Im5vcm1hbCJ9.IB53oaHaRTSY0DyTLwhcgKYvWDDIo6lb8iqFtkGlm0oivv3C0Sqeczt-EYUkmG3XkfgiGjaIjK1zGQP4M439i13z4VnAVM8xuWbMZcEawjPtxflzBvKNcK3JLIh0uNPEONe89luWcnrUVZptEszPMg4-NQIZz5A4CatO1HDXPBtPabYKign9ST95tQsa6SDhLwqbJcqPrcxeU60FOK2wUeLcNWcNVjQELeJRRggdCMaCko_8poaimghGGEe2eohxaZPmvnRRv9vReFCjHy6pl7dGsCoeK1mptcSMER2jRjEzc_G6A43yBuo5WYVOlx4u_ACkEllsTqRBEjWPAWCATw";
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn20wneIUBr2PCf8i5iWjviwDcgFbrg//xt+HonfQd6PLqA6Ceu5sUc8gib2S9ogFXLwc95576FIAHNSrmr0fy03M7Hd39zxROSfqFc7s9UCpX6yzQPz0eg0joozmAadwKLD+HpTs3icUruaRCTLgu4azS6EL6O+EbMaMj3goDe2RbZeZSi8qEsfBNH/x67SAdMvNK8AFYh2ycavr9MfbPk4EhJk3cBzsizrbrmlEBcsSeT3cB0hWga8P16BRGvpi8ZGoyspFK6RE5IX/L61/tAMu2lMdl+ST+17YxIVwxRBBBVlaqNUgne1x+rrlRvSFPL3R4Qqtm4oNR7puMATxTwIDAQAB-----END PUBLIC KEY-----";

        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();

        JWT jwt1 = JWTUtil.parseToken(token);

        // {"address":"zs","role":"admin,user","nickname":"tomcat"}
        System.out.println(claims);
    }

}
