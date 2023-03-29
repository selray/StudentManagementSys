package com.example.springboot.service;

import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sel
 * @since 2023-03-29
 */
public interface IStudentService extends IService<Student> {
    boolean login(UserDTO userDTO);
}
