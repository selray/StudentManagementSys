package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.Lessonchoose;
import com.example.springboot.entity.Student;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sel
 * @since 2023-04-02
 */
public interface ILessonchooseService extends IService<Lessonchoose> {

    Page<Student> getClassStudents(Page<Object> page, Integer tnumber, Integer lnumber);

    List<Student> listClassStudents(Integer tnumber, Integer lnumber);
}
