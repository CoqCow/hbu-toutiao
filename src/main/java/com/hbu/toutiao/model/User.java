package com.hbu.toutiao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Lenovo on 2017/12/25.
 */
@Component
@Getter
@Setter
@ToString
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String salt;
    private String headUrl;
    private Date last_login_dt;
    private Date last_change_time;
    public User() {

    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }
}
