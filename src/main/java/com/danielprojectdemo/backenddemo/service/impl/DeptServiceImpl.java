package com.danielprojectdemo.backenddemo.service.impl;

import com.danielprojectdemo.backenddemo.aop.MyLog;
import com.danielprojectdemo.backenddemo.mapper.DeptMapper;
import com.danielprojectdemo.backenddemo.mapper.EmpMapper;
import com.danielprojectdemo.backenddemo.pojo.Dept;
import com.danielprojectdemo.backenddemo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @MyLog
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    @MyLog
    @Override
    public List<Dept> getDeptById(Integer id) {
        return deptMapper.dept(id);
    }

    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

//    @Transactional(rollbackFor = Propagation.REQUIRES_NEW)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        deptMapper.deleteById(id);
        empMapper.deletByDeptId(id);

    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.addNewDept(dept);
    }
}
