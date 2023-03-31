package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.Teacher;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.TeacherMapper;
import com.example.springboot.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sel
 * @since 2023-03-31
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    private static final Log LOG = Log.get();
    @Override
    public UserDTO login(UserDTO userDTO) {
        //去数据库查询的实体类,查找是否有响应数据
        Teacher one = getUserInfo(userDTO);
        if(one != null) {
            BeanUtil.copyProperties(one,userDTO,true);
            return  userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }



    }
    private Teacher getUserInfo(UserDTO userDTO){
        QueryWrapper<Teacher> queryWrapper= new QueryWrapper<>();
        //校验输入的信息是否在数据库中，成功就返回用户信息以便于浏览器后面处理
        queryWrapper.eq("tusername",userDTO.getUsername());
        queryWrapper.eq("tpassword",userDTO.getPassword());

        Teacher one;
        try{
            one = getOne(queryWrapper); //从数据库查询用户信息
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
}
