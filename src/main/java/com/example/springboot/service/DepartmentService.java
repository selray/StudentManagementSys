package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.department;
import com.example.springboot.mapper.departmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface DepartmentService extends IService<department> {

    //boolean login(UserDTO userDTO);
}
