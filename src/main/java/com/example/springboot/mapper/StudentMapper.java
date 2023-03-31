package com.example.springboot.mapper;

import com.example.springboot.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sel
 * @since 2023-03-29
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
