package com.hbu.toutiao.service;

import com.hbu.toutiao.model.User;
import com.hbu.toutiao.util.JedisAdapter;
import com.hbu.toutiao.util.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lenovo on 2018/4/17.
 */
@Service
public class MailServicee {
    @Autowired
    MailSender mailSender;
    @Autowired
    UserService userService;
    @Autowired
    JedisAdapter jedisAdapter;
    private static final Logger logger = LoggerFactory.getLogger(MailServicee.class);

    //发送邮件获得注册码
    public Map<String, Object> sendMail(String to){
        Map<String, Object> hashMap=new HashMap();
        //先判断邮件是否注册过
        if(userService.getUserByName(to)!=null){
            //注册过了
            hashMap.put("code",-1);
            hashMap.put("msg","该邮箱已经注册过了");
            return hashMap;
        }
        Map<String, Object> map = new HashMap();
        map.put("username", "邮件测试");
        map.put("username","牛若冰");//registcode
        Random random=new Random();
        int a=random.nextInt(99999);
        if(a<1000){
            a=a+1000;
        }
        map.put("registcode",a);
        boolean b= mailSender.sendWithHTMLTemplate(to, "河大头条注册码",
                "mails/welcome.vm", map);

        if(b){
            //发送成功了，注册码在redis中保存两分钟
            jedisAdapter.addMial(to,a+"");
            hashMap.put("code",a);
            hashMap.put("msg","注册码成功下发");
        }else {
            //发送失败
            hashMap.put("code",-2);
            hashMap.put("msg","注册码下发失败，邮箱不合法");
        }

        return  hashMap;

    }




}
