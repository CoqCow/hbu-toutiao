package com.hbu.toutiao.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Lenovo on 2018/3/6.
 */
@Component
public class LoginUser {
    private static HashMap<Integer,Date> hashMap=new HashMap<>();
   // private static HashMap<Integer,User> loginUser=new HashMap<>();
    public boolean get(int id){
        if(hashMap.get(id)==null){
            return false;
        }else {
            return true;
        }
    }

    public void set(int id){
        hashMap.put(id,new Date());

    }

    public void print(){

    }
}
