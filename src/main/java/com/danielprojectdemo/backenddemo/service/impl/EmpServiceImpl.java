package com.danielprojectdemo.backenddemo.service.impl;

import com.danielprojectdemo.backenddemo.mapper.EmpMapper;
import com.danielprojectdemo.backenddemo.pojo.Emp;
import com.danielprojectdemo.backenddemo.pojo.PageBean;
import com.danielprojectdemo.backenddemo.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    private Path imageDirectory;

    public EmpServiceImpl(@Value("${local.path.str}") String imageDirectory) {
        this.imageDirectory = Paths.get(imageDirectory);
    }
    @Autowired
    private EmpMapper empMapper;

    @Override
    public Resource getImage(String imageName) throws IOException {
        Path imagePath = imageDirectory.resolve(imageName);

        // 检查图片文件是否存在
        if (Files.exists(imagePath)) {
            // 读取图片文件并返回Resource对象
            Resource imageResource = new FileSystemResource(imagePath.toFile());
            return imageResource;
        } else {
            // 处理图片不存在的情况，例如返回一个默认图片
            // 或者抛出异常并返回合适的错误响应
            throw new FileNotFoundException("Image not found: " + imageName);
        }
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin, LocalDate end) {
        long count = empMapper.count();

        Integer start = (page - 1) * pageSize;
        List<Emp> empList = empMapper.page(start, pageSize, name, gender, begin, end);

        PageBean pageBean = new PageBean(count, empList);

        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        return emp;
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.update(emp);
    }

    @Override
    public Emp login(Emp emp) {
        return empMapper.getByUserNameAndPassward(emp);
    }


}
