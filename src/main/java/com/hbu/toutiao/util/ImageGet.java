package com.hbu.toutiao.util;

import java.util.Random;

/**
 * Created by Lenovo on 2018/1/2.
 */
public class ImageGet {

    public static void main(String[] args){
        Random random=new Random();
        String imageUrl=String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(10));

    }
}
