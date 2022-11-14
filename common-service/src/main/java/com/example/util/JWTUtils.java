package com.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtils {
    // 签名密钥
    private static final String SECRET = "!DAR$";

    public static void main(String[] args) {
        String token = getToken("gqk");
        System.out.println("token = " + token);
    }
    public static String getToken(String username) {
        String token = UUID.randomUUID().toString().replace("-", "");
        // put token to redis
        Map<String, String> payload = new HashMap<>(16);
        payload.put("token", token);
        payload.put("username", username);
        decodeBearer("Bearer " + getToken(payload, 1));
        return getToken(payload, 1);
    }
    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String getToken(Map<String,String> payload, int expireDays){
        // 指定token过期时间为7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, expireDays);

        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach((k,v) -> builder.withClaim(k,v));
        // 指定过期时间和签名算法
        String token = builder.withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token
     */
    public static String decodeBearer(String token){
        String BEARER = "Bearer ";
        int begin = token.indexOf(BEARER);
        token = token.substring(begin + BEARER.length());
        return token;
    }

    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT decode(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }
}