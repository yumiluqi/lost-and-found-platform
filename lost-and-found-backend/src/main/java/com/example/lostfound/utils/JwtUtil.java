package com.example.lostfound.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT工具类：生成token、解析token
 */
public class JwtUtil {

    // 密钥（生产环境建议放配置文件，大作业写死即可）
    private static final String SECRET = "lostfound-secret-key-2026-must-be-long-enough";
    // 过期时间：24小时
    private static final long EXPIRE = 24 * 60 * 60 * 1000L;

    private static SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /** 生成token，传入用户ID */
    public static String createToken(Integer userId, String username) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** 解析token，返回Claims；失败返回null */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
