package com.example.springboot.mapper;

import com.example.springboot.entity.student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface studentMapper {
    @Select("select * from student;")
    List<student> findAll();
}
