package com.danielprojectdemo.backenddemo.service;

import com.danielprojectdemo.backenddemo.pojo.Dept;

import java.util.List;

public interface DeptService {

    List<Dept> list();

    List<Dept> getDeptById(Integer id);

    void update(Dept dept);

    void delete(Integer id);

    void add(Dept dept);
}
