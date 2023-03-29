package com.example.springboot.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.department;
import com.example.springboot.mapper.StudentMapper;
import com.example.springboot.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sel
 * @since 2023-03-29
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    private static final Log LOG = Log.get();
    @Override
    public boolean login(UserDTO userDTO) {
        //去数据库查询的实体类,查找是否有响应数据
        QueryWrapper<Student> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("logn",userDTO.getUsername());
        queryWrapper.eq("pswd",userDTO.getPassword());
        try{
            Student one = getOne(queryWrapper);
            return one != null;
        }catch (Exception e){
            LOG.error(e);
            return false;
        }

    }
}
