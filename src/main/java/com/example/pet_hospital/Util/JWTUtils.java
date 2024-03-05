package com.example.pet_hospital.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private static String signKey = "stargazing0115";
    private static Long expire = 4320000L; //12 hours

    /**
     * 生成JWT令牌
     * @param claims  JWT第二部分负载
     * @return
     **/
    public static String jwtGenerater(Map<String,Object> claims){

        String jwt= Jwts.builder().
                addClaims(claims).
                signWith(SignatureAlgorithm.HS256,signKey).
                setExpiration(new Date(System.currentTimeMillis()+expire)).
                compact();
        return jwt;
    }

    /**
     * 生成JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载内容
     **/
    public static Claims jwtParser(String jwt){
        Claims claims = Jwts.parser().
                setSigningKey(signKey).
                parseClaimsJws(jwt).
                getBody();
        return claims;
    }

}

