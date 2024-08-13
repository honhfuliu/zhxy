package com.ziheng.zhxy.util;

import com.ziheng.zhxy.entity.TUser;
import com.ziheng.zhxy.service.ex.UnauthorizedAccessException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.UUID;

public class TokenUtil {
    private long time = 1000*60*60*2;
    private static final String SIGNATURE = "U2FsdGVkX19My/GXv83MJGlxFIjii2kCct0f8qDnHqcJ2YjHY/Xb15wsYDdjtwcsOSKdDw==";
    // 生成token
    public String createToken(TUser user) {
        JwtBuilder jwtBuilder = Jwts.builder();
        // 创建jwt的三部分
        String jwtToken = jwtBuilder
                // header
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg","HS256") //算法
                // payload 载荷信息
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .claim("uid", user.getUid())
                .setSubject("this is test token")// 主题
                .setExpiration(new Date(System.currentTimeMillis() + time)) // 有效时间
                .setId(UUID.randomUUID().toString())
                // signature 签名
                .signWith(SignatureAlgorithm.HS256, SIGNATURE)
                // 将三部分的数据仅拼接
                .compact();
        return jwtToken;
    }

    // 获取用户的信息
    public static TUser getTokenTuser(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(SIGNATURE).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        TUser user = new TUser();
        user.setUsername((String) body.get("username"));
        user.setRole((String) body.get("role"));
        user.setUid((Integer) body.get("uid"));
        return user;
    }

    // token 验证
    public static boolean verifyToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(SIGNATURE).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new UnauthorizedAccessException("非法访问，你是不是有病");
        }

    }

}
