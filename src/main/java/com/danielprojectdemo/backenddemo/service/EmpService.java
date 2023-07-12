package com.danielprojectdemo.backenddemo.service;

import com.danielprojectdemo.backenddemo.pojo.Emp;
import com.danielprojectdemo.backenddemo.pojo.PageBean;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    Resource getImage(String imageName) throws IOException;

    PageBean page(Integer page, Integer pageSizeString, String name, Short gender, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    void save(Emp emp);

    Emp getById(Integer id);

    void update(Emp emp);

    Emp login(Emp emp);
}
