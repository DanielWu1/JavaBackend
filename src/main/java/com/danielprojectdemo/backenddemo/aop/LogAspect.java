package com.danielprojectdemo.backenddemo.aop;

import com.alibaba.fastjson.JSONObject;
import com.danielprojectdemo.backenddemo.mapper.OperateLogMapper;
import com.danielprojectdemo.backenddemo.pojo.OperateLog;
import com.danielprojectdemo.backenddemo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.danielprojectdemo.backenddemo.anno.Log)")
    public Object recordLog(ProceedingJoinPoint joinPoint) throws Throwable {

        String jwt = request.getHeader("token");
        Claims claims = JwtUtils.testParseJwt(jwt);
        Integer operateUser = (Integer) claims.get("id");

        // time
        LocalDateTime operateTime = LocalDateTime.now();

        // class name
        String className = joinPoint.getTarget().getClass().getName();

        //method name
        String methodName = joinPoint.getSignature().getName();

        //args name
        Object[] args = joinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        // start time
        Long begin = System.currentTimeMillis();

        //return
        Object result = joinPoint.proceed();

        //end
        Long end = System.currentTimeMillis();

        // return value to JSON
        String returnValue = JSONObject.toJSONString(result);

        // proceed time
        Long costTime = end - begin;

        OperateLog operateLog = new OperateLog(null, operateUser, operateTime, className, methodName, methodParams, returnValue, costTime);
        operateLogMapper.insert(operateLog);

        log.info("acp {}", operateLog);

        return result;
    }

}
