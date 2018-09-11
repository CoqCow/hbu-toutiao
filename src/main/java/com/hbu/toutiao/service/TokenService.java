package com.hbu.toutiao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hbu.toutiao.ToutiaoApplication;
import com.hbu.toutiao.dao.UserDAO;
import com.hbu.toutiao.dto.LoginDto;
import com.hbu.toutiao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

@Service
public class TokenService {

    @Autowired
    private ToutiaoApplication.Config config;

    @Autowired
    private UserDAO userDAO;
    //登录逻辑
    public String creatToken(LoginDto loginDto){
        User user=userDAO.selectByEmail(loginDto.email);
        if(user==null){

        }
        return "";
    }

    //创建Token
    private String creatToken(int userId,String email) throws UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(config.jwtSecret);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);

        return JWT.create()
                .withIssuer("user_module")
                .withExpiresAt(calendar.getTime())
                .withClaim("userId", userId)
                .withClaim("email", email)
                .sign(algorithm);
    }
}
