package com.example.controller;

import com.example.jwt.Md5;
import com.example.service.redis.RedissonClientService;
import com.example.util.JWTUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.util.Constants.USERNAME_TOKEN;

@RestController
@RequestMapping("auth")
@ApiOperation("鉴权")
public class LoginController {
    @Autowired
    private RedissonClientService redissonClientService;
    @RequestMapping("/a")
    public String a(int i) {
        return "hello a " + i;
    }

    @RequestMapping("/login")
    @ApiOperation("登录接口")
    public String a(String username, String password) {
        String pwd = "123456A~";// search username from db and get pwd
        if (password.equals(Md5.getMd5Str(pwd))) {
            String jwt = JWTUtils.getToken(username);
            redissonClientService.setCache(username+USERNAME_TOKEN, jwt);
            return jwt;
        }
        return "false";
    }
    @RequestMapping("/login/md5")
    @ApiOperation("获取登录的加密密码")
    public String password(String password) {
        if (password.isEmpty()) {
            return "false";
        }
        return Md5.getMd5Str(password);
    }
}
