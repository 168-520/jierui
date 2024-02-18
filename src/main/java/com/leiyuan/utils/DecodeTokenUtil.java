package com.leiyuan.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

/**
 * 返回当前登录用户名
 */
public class DecodeTokenUtil {

    public static String getUsername(String token){
        JwtParser parser = Jwts.parser();
        //解析token的SigningKey必须和⽣成token时设置密码⼀致
        parser.setSigningKey("leiyuan666");
        //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
        Jws<Claims> claimsJws =parser.parseClaimsJws(token);
        return claimsJws.getBody().get("username").toString();
    }

    public static String getUserMail(String token){
        JwtParser parser = Jwts.parser();
        //解析token的SigningKey必须和⽣成token时设置密码⼀致
        parser.setSigningKey("leiyuan666");
        //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
        Jws<Claims> claimsJws =parser.parseClaimsJws(token);
        return claimsJws.getBody().get("mail").toString();
    }
}
