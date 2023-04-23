package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sel
 * @since 2023-03-29
 */
public interface IStudentService extends IService<Student> {
    UserDTO login(UserDTO userDTO);
    //因为需要注册的信息放入数据库中，所以返回实体对象
    Student register(UserDTO userDTO);
}
