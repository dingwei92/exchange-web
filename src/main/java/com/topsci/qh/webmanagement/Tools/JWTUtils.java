package com.topsci.qh.webmanagement.Tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Created by starry on 2018/2/11.
 */
public class JWTUtils {
    public static final int TOKEN_TIME = 1;

    /**
     * 获取token
     * token 组成  前缀 MD5(username)jwttoken
     */
    public static String getToken(String username){
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*360*100))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret") //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
        String tokenStr =  "Bearer "+ new MD5Tool().getDigestPassword(username) + token;
        return tokenStr;
    }

    /**
     * jwt 通过token获取username
     * @param token
     * @return
     */
    public static String getUserName(String token){
        String user = Jwts.parser()
                .setSigningKey("MyJwtSecret")
                .parseClaimsJws(token.replace("Bearer ", "").substring(32))
                .getBody()
                .getSubject();
        return user;
    }
}
