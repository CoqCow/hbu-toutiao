package com.hbu.toutiao.controller;


import com.alibaba.fastjson.JSON;
import com.hbu.toutiao.service.QiniuService;
import com.hbu.toutiao.util.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by nowcoder on 2016/6/26.
 */
@Controller
public class SettingController {
    @Autowired
    QiniuService qiniuService;
    @Autowired
    MailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
    @RequestMapping("/setting")
    @ResponseBody
    public String setting(int id){
        new Date();
        return "Setting:OK";
    }

    @RequestMapping("/sendmail")
    @ResponseBody
    public String sendMail(){
        Map<String, Object> map = new HashMap();
        map.put("username", "邮件测试");
        map.put("username","牛若冰");//registcode
        Random random=new Random();
        int a=random.nextInt(99999);
        if(a<1000){
            a=a+1000;
        }
        map.put("registcode",a);
        mailSender.sendWithHTMLTemplate("1344729152@qq.com", "登陆异常",
                "mails/welcome.vm", map);

    return  "send";
    }
    @RequestMapping("/around")
    @ResponseBody
    public String around(){
        logger.info("这个方法");
        return "执行方法";
    }

    @RequestMapping("/toregister")
    public String toregister(){
        logger.info("这个方法");
        return "register";
    }
}
