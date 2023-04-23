package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.controller.dto.TeacherDTO;
import com.example.springboot.entity.Teacher;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sel
 * @since 2023-03-31
 */
public interface ITeacherService extends IService<Teacher> {

    TeacherDTO login(TeacherDTO teacherDTO);

    Teacher register(TeacherDTO teacherDTO);
}
