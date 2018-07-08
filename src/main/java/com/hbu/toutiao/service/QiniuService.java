package com.hbu.toutiao.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hbu.toutiao.util.ToutiaoUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.crypto.Mac;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by nowcoder on 2016/7/7.
 */
@Service
public class QiniuService {
    //public static Zone zone = Zone.zone1();

    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);
    private static String QINIU_IMAGE_DOMAIN = "http://p5mgpfjck.bkt.clouddn.com/";
    //http://p5mgpfjck.bkt.clouddn.com/8c347bb8323c4fe79f30c63ff7d40176.jpg
    //http://p5mgpfjck.bkt.clouddn.com/01.jpg
    //
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "10NXVqLEZ7IqlTk8jPS-7SKL8aWxXlSWLTU6hzYx";//更改
    String SECRET_KEY = "y9U3HqvkYxi1CIpVBrcwtGMKWFHIe3frUAXXb_0U";//更改
    String bucketname = "image";
    String key = "01.jpg";
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    public String getUpToken(){

        String s=auth.uploadToken(bucketname);
        System.out.println(s);
        return s;
    }
    Configuration cfg = new Configuration(Zone.zone1());
    UploadManager uploadManager = new UploadManager(cfg);
    public String upload(MultipartFile file) {
        try {
            int pointIndex=file.getOriginalFilename().lastIndexOf(".");
            if(pointIndex<0){
                return null;
            }
            String fileType=file.getOriginalFilename().substring(pointIndex+1).toLowerCase();
            if(!ToutiaoUtil.isFileAllowed(fileType)){
                return null;
            }
            String filename=UUID.randomUUID().toString().replace("-","")+"."+fileType;

            //调用put方法上传
            Response res = null;
            res = uploadManager.put(file.getBytes(), filename, getUpToken());

            //打印返回的信息

            System.out.println("isok="+res.isOK());

            System.out.println("body="+res.bodyString());
            if(res.isOK()&&res.isJson()){
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            }else{
                return null;
            }

        } catch (QiniuException e) {
            e.printStackTrace();
            return null;
/*            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }*/
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

/*    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!ToutiaoUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传file.getInputStream()
            String path="D:\\upload\\test.png";
            Response res = uploadManager.put(path, fileName, getUpToken());
            //打印返回的信息
            System.out.println(res.toString());
            return  null;
*//*            if (res.isOK() && res.isJson()) {
                return QINIU_IMAGE_DOMAIN + JSONObject.parseObject(res.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + res.bodyString());
                return null;
            }*//*
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            e.printStackTrace();
            logger.error("七牛异常:" + e.getMessage());
            return null;
        }
    }*/
}
