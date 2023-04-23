package com.example.springboot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.department;
import com.example.springboot.mapper.departmentMapper;
import com.example.springboot.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<departmentMapper, department> implements DepartmentService {


//    @Override
//    public boolean login(UserDTO userDTO) {
//        //去数据库查询的实体类,查找是否有响应数据
//        QueryWrapper<department> queryWrapper= new QueryWrapper<>();
//        queryWrapper.eq("username",userDTO.getUsername());
//        queryWrapper.eq("password",userDTO.getPassword());
//        department one = getOne(queryWrapper);
//        return one != null;
//    }
}
