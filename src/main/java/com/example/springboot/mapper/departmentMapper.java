package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface departmentMapper extends BaseMapper<department> {

}
