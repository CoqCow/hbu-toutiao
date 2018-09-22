package com.hbu.toutiao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hbu.toutiao.ToutiaoApplication;
import com.hbu.toutiao.dao.UserDAO;
import com.hbu.toutiao.dto.LoginDto;
import com.hbu.toutiao.exception.InvalidLoginInfoException;
import com.hbu.toutiao.exception.JWTTokenExpiredException;
import com.hbu.toutiao.exception.JWTTokenInvalidException;
import com.hbu.toutiao.model.User;
import com.hbu.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private ToutiaoApplication.Config config;

    @Autowired
    private UserDAO userDAO;

    //登录逻辑
    public String creatToken(LoginDto loginDto) throws InvalidLoginInfoException, UnsupportedEncodingException {
        User user = userDAO.selectByEmail(loginDto.email);
        System.out.println(user.toString());
        if (user == null || !ToutiaoUtil.MD5(loginDto.password + user.getSalt()).equals(user.getPassword())) {
            throw new InvalidLoginInfoException();
        }
        user.setLast_login_dt(new Date());
        String token = creatToken(user.getId(), user.getEmail());
        return token;
    }

    //创建Token
    private String creatToken(int userId, String email) throws UnsupportedEncodingException {
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

    //刷新token
    public String refreshToken(String oldToken) throws UnsupportedEncodingException, JWTTokenExpiredException, JWTTokenInvalidException {
        DecodedJWT decodedJWT = validateToken(oldToken);
        int id = decodedJWT.getClaim("userId").asInt();
        User user = userDAO.selectById(id);
        return creatToken(user.getId(), user.getEmail());

    }

    //验证token
    public DecodedJWT validateToken(String token)
            throws UnsupportedEncodingException, JWTTokenExpiredException, JWTTokenInvalidException {
        Algorithm algorithm = Algorithm.HMAC256(config.jwtSecret);

        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            return verifier.verify(token);

        } catch (TokenExpiredException e1) {
            throw new JWTTokenExpiredException();
        } catch (JWTVerificationException e2) {
            throw new JWTTokenInvalidException();
        }
    }
}
