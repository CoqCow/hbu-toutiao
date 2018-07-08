package com.hbu.toutiao.controller;

import com.hbu.toutiao.model.EntityType;
import com.hbu.toutiao.model.HostHolder;
import com.hbu.toutiao.model.News;
import com.hbu.toutiao.model.ViewObject;
import com.hbu.toutiao.service.LikeService;
import com.hbu.toutiao.service.NewsService;
import com.hbu.toutiao.service.QiniuService;
import com.hbu.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class HomeController {
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;
    @Autowired
    QiniuService qiniuService;
    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = newsService.getLatestNews(userId, offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            if (localUserId != 0) {
              //  System.out.println("status"+likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
                vo.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
            } else {
                vo.set("like", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,@RequestParam(value = "pop", defaultValue= "1") int pop) {
        model.addAttribute("vos", getNews(0, 0, 10));
        if (hostHolder.getUser() != null) {
            pop = 0;
        }
        model.addAttribute("pop", pop);
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }
    //图片上传
    @RequestMapping(path = {"/user/toSetAvatar"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String toSetAvatar() {

        return "uplodeImg";
    }
    //
    @RequestMapping(path = {"/user/setAvatar"}, method = {RequestMethod.GET, RequestMethod.POST})
            public String setAvatar(Model model,@RequestParam("file") MultipartFile file) {
            String imageUrl=qiniuService.upload(file);

            System.out.println("imageUrl="+imageUrl);
            model.addAttribute("imageUrl",imageUrl);

        return "uplodeImg";
    }

    @RequestMapping(path = {"/user/addnews"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String addnews(String title,String text) {

        System.out.println("数据"+text+title);
        return "uplodeImg";
    }
}
