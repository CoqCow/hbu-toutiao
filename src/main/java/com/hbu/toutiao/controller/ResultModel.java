package com.hbu.toutiao.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResultModel {
    public Date timestamp = new Date();
    public String message;
    public String path;

    public ResultModel(ResultMsg resultMsg, String path) {
        this.message = resultMsg.message;
        this.path = path;
    }
}
