package com.hbu.toutiao.interceptor;


import com.hbu.toutiao.model.HostHolder;
import com.hbu.toutiao.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by nowcoder on 2016/7/3.
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private LoginUser loginUser;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

      // System.out.println("id="+httpServletRequest.getParameter("id"));
        int id=Integer.parseInt(httpServletRequest.getParameter("id"));
        if(loginUser.get(id)==false){
            System.out.println("没有登录");
            loginUser.set(id);
        }else {
            System.out.println("已经登录");
        }
       ///


        if (hostHolder.getUser() == null) {
            httpServletResponse.sendRedirect("/?pop=1");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
