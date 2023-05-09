package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Lessonchoose;
import com.example.springboot.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sel
 * @since 2023-04-02
 */
@Mapper
public interface LessonchooseMapper extends BaseMapper<Lessonchoose> {

    Page<Student> getClassStudents(Page<Object> page, @Param("tnumber")Integer tnumber,@Param("lnumber") Integer lnumber);

    List<Student> listClassStudents(Integer tnumber, Integer lnumber);
}
