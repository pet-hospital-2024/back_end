package com.example.pet_hospital.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static String signKey = "stargazing0115";
    private static Long expire = 43200000L; //12 hours

    private static Long expire_short = 1L; //1 m_second

    private static Long expire_years = 31536000000L; //1 m_second

    private static Long expire_30_min = 1800000L; //30mins
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

    public static boolean refreshTokenNeeded(String token){//如果令牌有效期小于1小时则返回true。
        Claims claims = JWTUtils.jwtParser(token);
        long expireTime = claims.getExpiration().getTime();
        long currentTime = System.currentTimeMillis();
        return expireTime - currentTime <= 3600000L;
    }

    public static String newToken(String Authorization){
        Claims claims=JWTUtils.jwtParser(Authorization);
        String username=(String) claims.get("username");
        String user_id=(String) claims.get("user_id");
        String identity=(String) claims.get("identity");

        HashMap<String,Object> newclaim=new HashMap<>();
        newclaim.put("username",username);
        newclaim.put("user_id",user_id);
        newclaim.put("identity",identity);
        String token =JWTUtils.jwtGenerater(newclaim);
        return token;
    }


}

