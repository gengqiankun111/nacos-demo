package com.example.service.redis;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.util.Constants.USERNAME_TOKEN;


@Service
public class JwtService {
    @Autowired
    private RedissonClientService redissonClientService;

    public boolean checkJwt(String jwt) {
        try {
            // 1.校验JWT字符串
            jwt = JWTUtils.decodeBearer(jwt);
            DecodedJWT decodedJWT = JWTUtils.decode(jwt);

            // 2.取出JWT字符串载荷中的随机token，从Redis中获取用户信息
            String username = decodedJWT.getClaim("username").asString();
            //String token = decodedJWT.getToken();
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            if (redissonClientService.getCache(username+USERNAME_TOKEN).equals(jwt)) {
                return true;
            }

            // if 过期时间小于60秒，返回token给前端
        }catch (SignatureVerificationException e){
            System.out.println("无效签名");
            e.printStackTrace();
        }catch (TokenExpiredException e){
            System.out.println("token已经过期");
            e.printStackTrace();
        }catch (AlgorithmMismatchException e){
            System.out.println("算法不一致");
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("token无效");
            e.printStackTrace();
        }
        return false;
    }
}
