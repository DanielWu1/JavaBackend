package com.danielprojectdemo.backenddemo.filter;

import com.alibaba.fastjson.JSONObject;
import com.danielprojectdemo.backenddemo.pojo.Result;
import com.danielprojectdemo.backenddemo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String url = req.getRequestURL().toString();
        log.info("request url{}", url);

        if(url.contains("login")){
            log.info("login check");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String jwt = req.getHeader("token");
        System.out.println(jwt);
        if(!StringUtils.hasLength(jwt)){
            log.info("token is empty");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        try{
            JwtUtils.testParseJwt(jwt);
        }
        catch (Exception e){
            e.printStackTrace();
            log.info("parse error");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            resp.getWriter().write(notLogin);
            return;
        }
        log.info("token accept");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
