package com.ziheng.zhxy.util;

import com.ziheng.zhxy.entity.TUser;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

public class tokeTest {

    private long time = 1000*60*60*24;
    private String signature = "admin";

    @Test
    public void jwt_test01() {
        JwtBuilder jwtBuilder = Jwts.builder();
        // 创建jwt的三部分
        String jwtToken = jwtBuilder
                // header
                .setHeaderParam("typ", "jwt")
                .setHeaderParam("alg","HS256") //算法
                // payload 载荷信息
                .claim("username", "tom")
                .claim("role", "ADMIN")
                .setSubject("admin-test")// 主题
                .setExpiration(new Date(System.currentTimeMillis() + time)) // 有效时间
                .setId(UUID.randomUUID().toString())
                // signature 签名
                .signWith(SignatureAlgorithm.HS256, signature)
                // 将三部分的数据仅拼接
                .compact();
        System.out.println(jwtToken);

    }

    // 解密
    @Test
    void parse(){
        String token = "eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJBRE1JTiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE3MDcyMDE1NTUsImp0aSI6IjIxMDFhNTQwLTM0ZmItNDM2OC05OGI1LWFhNTBhNzcxMjliZSJ9.VkCstNmNOMXKul2Zl4Uc7aePdW0SQClay-amffEEhF0";
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        System.out.println(body.get("username"));
        System.out.println(body.get("role"));
        System.out.println(body.getId());
        System.out.println(body.getSubject());
        System.out.println(body.getExpiration());


    }

    @Test
    void test03() {
        TUser user = new TUser();
        user.setUsername("admin");
        user.setRole("RENCAI");
        TokenUtil tokenUtil = new TokenUtil();
        String token = tokenUtil.createToken(user);
        System.out.println(token);

        boolean b = tokenUtil.verifyToken(token);
        System.out.println(b);

    }

    @Test
    void test04() {
        TokenUtil tokenUtil = new TokenUtil();
        String token = "eyJ0eAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwicm9sZSI6IlJFTkNBSSIsInN1YiI6InRoaXMgaXMgdGVzdCB0b2tlbiIsImV4cCI6MTcwNzEyMzEzNywianRpIjoiNzE5YWI4YzAtYzEyYS00NWJkLWIzZGQtNjE0MmMxYTQ5Njg1In0.bccLcA8C8W_z93o7zyKhwn3zizUqw0QqOO7kEH4MRXg";
        boolean b = tokenUtil.verifyToken(token);
        System.out.println(b);
    }
}
