package com.hbu.toutiao.controller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultMsg {
    EMAIL_IS_EXIST(1001, "Email registrado."),
    EMAIL_NOT_REGISTER(1002, "Mail is not registered."),
    EMAIL_OF_PASSWORD_ERROR(1003, "Mail or password error"),
    TOKEN_EXPIRED(1004, "Login expired, please login again."),
    TOKEN_INVALID(1005, "Validation failed. Please login again."),
    ;
    public int status;
    public String message;
}
