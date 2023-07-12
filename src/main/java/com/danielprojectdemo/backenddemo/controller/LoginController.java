package com.danielprojectdemo.backenddemo.controller;

import com.danielprojectdemo.backenddemo.pojo.Emp;
import com.danielprojectdemo.backenddemo.pojo.Result;
import com.danielprojectdemo.backenddemo.service.EmpService;
import com.danielprojectdemo.backenddemo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result login(@RequestBody Emp emp){
        log.info("user login: {}", emp);
        Emp e = empService.login(emp);

        if(e != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("name", e.getName());
            claims.put("username", e.getUsername());

            String jwt = JwtUtils.testGenJwt(claims);
            log.info(jwt);
            return Result.success(jwt);
        }
        return Result.error("username or password wrong");
    }
}
