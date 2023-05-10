package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

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
    @Select("CALL deletestudent(#{targetid})")
    void deleteStudent(@Param("targetid") int targetid);
}