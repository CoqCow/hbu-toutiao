package com.hbu.toutiao.controller;

import com.hbu.toutiao.dto.LoginDto;
import com.hbu.toutiao.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Controller
public class UserController {
    @Autowired
    private TokenService tokenService;

    @RequestMapping(path = "/tokens", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createToken(@RequestBody LoginDto loginDto)
            throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException {
        try {
            String token = tokenService.creatToken(loginDto);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("x");
        }
    }
}
