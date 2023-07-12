package com.danielprojectdemo.backenddemo.mapper;

import com.danielprojectdemo.backenddemo.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select * from dept")
    List<Dept> list();

    @Select("select * from dept where id = #{id}")
    List<Dept> dept(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);

    @Delete("delete from dept where id = #{id}")
    void deleteById (Integer id);

    @Insert("insert into dept (name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    void addNewDept(Dept dept);
}
