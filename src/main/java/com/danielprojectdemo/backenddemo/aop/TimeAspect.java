package com.danielprojectdemo.backenddemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.Arrays;

@Slf4j
//@Component
//@Aspect
public class TimeAspect {


    //    @Pointcut("execution(* com.danielprojectdemo.backenddemo.service.impl.DeptServiceImpl.getDeptById(Integer))")
//    @Pointcut("execution(* com.*.backenddemo.service.impl.DeptServiceImpl.*(Integer))")
//    @Pointcut("execution(* com..DeptService.*(..))")
//    @Pointcut("execution(* com.danielprojectdemo.backenddemo.service.DeptService.getDeptById(Integer)) ||" +
//            "execution(* com.danielprojectdemo.backenddemo.service.DeptService.list())")
    @Pointcut("@annotation(com.danielprojectdemo.backenddemo.aop.MyLog)")
    private void pt() {
    }

    ;


//    @Around("execution( * com.danielprojectdemo.backenddemo.service.*.*(..))")
//    @Around("execution(public * com.danielprojectdemo.backenddemo.service.impl.DeptServiceImpl.getDeptById(java.lang.Integer))")
    @Around("pt()")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("AOP time aspect");

        // target class name
        String className = joinPoint.getTarget().getClass().getName();
        log.info("class name {}", className);
        // target method name
        String methodName = joinPoint.getSignature().getName();
        log.info("methodName {}", methodName);
        // parameter name
        Object[] args = joinPoint.getArgs();
        log.info("args {}", Arrays.toString(args));

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info(joinPoint.getSignature() + "time {}ms", end - start);

        return result;
    }

//    @Before("pt()")
//    public void printStr(JoinPoint joinPoint){
//        log.info("before test");
//    }


}
