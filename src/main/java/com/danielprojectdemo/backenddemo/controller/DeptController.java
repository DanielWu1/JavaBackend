package com.danielprojectdemo.backenddemo.controller;

import com.danielprojectdemo.backenddemo.anno.Log;
import com.danielprojectdemo.backenddemo.pojo.Dept;
import com.danielprojectdemo.backenddemo.pojo.Result;
import com.danielprojectdemo.backenddemo.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

//    private static Logger log = (Logger) LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;
//    @RequestMapping(value = "/depts", method = RequestMethod.GET)

    @GetMapping
    public Result list(){
        log.info("all dept");

        List<Dept> deptList = deptService.list();

        return Result.success(deptList);
    }
    @Log
    @GetMapping("/{id}")
    public Result dept(@PathVariable Integer id){
        log.info("dept by ID");
        List<Dept> dept = deptService.getDeptById(id);
        return Result.success(dept);
    }
    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("update dept");
        deptService.update(dept);
        return Result.success();
    }
    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("delete dept");
        deptService.delete(id);
        return Result.success();
    }
    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("add new dept");
        deptService.add(dept);
        return Result.success();
    }
}
