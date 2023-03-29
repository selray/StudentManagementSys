package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.department;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface departmentMapper extends BaseMapper<department> {

}
