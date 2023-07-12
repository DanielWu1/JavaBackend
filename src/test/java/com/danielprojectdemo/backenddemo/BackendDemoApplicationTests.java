package com.danielprojectdemo.backenddemo;

import com.danielprojectdemo.backenddemo.service.DeptService;
import com.danielprojectdemo.backenddemo.service.impl.DeptServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BackendDemoApplicationTests {

    @Autowired
    private DeptService deptService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGenJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("name","Tom");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "Daniel")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() * 3600 * 1000))
                .compact();
        System.out.println(jwt);
    }

    @Test
    public void testParseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("Daniel")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiVG9tIiwiaWQiOjEsImV4cCI6NjA4MDMwNzk3MjU5ODgwMH0.LVq2N-Z4qFPrxrS7v8CHByjbECB0CamGgX3uJXPVdQk")
                .getBody();
        System.out.println(claims);
    }

    @Test
    public void testAOP(){
//        deptService.list();
        deptService.getDeptById(2);
    }
}
