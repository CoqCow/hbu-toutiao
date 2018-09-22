package com.hbu.toutiao.controller;

import com.hbu.toutiao.dto.LoginDto;
import com.hbu.toutiao.exception.InvalidLoginInfoException;
import com.hbu.toutiao.exception.JWTTokenExpiredException;
import com.hbu.toutiao.exception.JWTTokenInvalidException;
import com.hbu.toutiao.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping(path = "/tokens", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createToken(@RequestBody LoginDto loginDto) throws UnsupportedEncodingException {
        try {
            String token = tokenService.creatToken(loginDto);
            return ResponseEntity.ok(token);
        } catch (InvalidLoginInfoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultModel(ResultMsg.EMAIL_OF_PASSWORD_ERROR,"/tokens"));
        }
    }

    @RequestMapping(path = "/tokens/refresh", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity refreshToken(@RequestParam(name = "oldToken") String oldToken) throws UnsupportedEncodingException {
        try {
            String token = tokenService.refreshToken(oldToken);
            return ResponseEntity.ok(token);
        } catch (JWTTokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultModel(ResultMsg.TOKEN_EXPIRED,"/tokens/refresh"));
        } catch (JWTTokenInvalidException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResultModel(ResultMsg.TOKEN_INVALID,"/tokens/refresh"));
        }
    }
}
