package com.danielprojectdemo.backenddemo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.danielprojectdemo.backenddemo.pojo.Result;
import com.danielprojectdemo.backenddemo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        System.out.println("preHandle ...");
        String url = req.getRequestURL().toString();
        log.info("request url{}", url);

        if(url.contains("login")){
            log.info("login check");
            return true;
        }
        String jwt = req.getHeader("token");

        System.out.println(jwt);
        if(!StringUtils.hasLength(jwt)){
            log.info("token is empty");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            res.getWriter().write(notLogin);
            return false;
        }
        try{
            JwtUtils.testParseJwt(jwt);
        }
        catch (Exception e){
            e.printStackTrace();
            log.info("parse error");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            res.getWriter().write(notLogin);
            return false;
        }
        log.info("token accept");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle ...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion ...");
    }
}
