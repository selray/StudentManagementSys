package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Constants;
import com.example.springboot.controller.dto.TeacherDTO;
import com.example.springboot.entity.Teacher;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.TeacherMapper;
import com.example.springboot.service.ITeacherService;
import com.example.springboot.utils.TokenUtils;
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
    public TeacherDTO login(TeacherDTO teacherDTO) {
        //去数据库查询的实体类,查找是否有响应数据
        Teacher one = getUserInfo(teacherDTO);
        if(one != null) {
            BeanUtil.copyProperties(one,teacherDTO,true);
            //设置token
            String token = TokenUtils.genToken(one.getTnumber().toString(),one.getTpassword());
            teacherDTO.setToken(token);
            return  teacherDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public Teacher register(TeacherDTO teacherDTO) {
        Teacher one = getUserInfo(teacherDTO);
        if(one==null){
            one=new Teacher();
            BeanUtil.copyProperties(teacherDTO,one,true);
            save(one);   // 把copy完之后的对象存储到数据库
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }

    private Teacher getUserInfo(TeacherDTO teacherDTO){
        QueryWrapper<Teacher> queryWrapper= new QueryWrapper<>();
        //校验输入的信息是否在数据库中，成功就返回用户信息以便于浏览器后面处理
        queryWrapper.eq("tusername",teacherDTO.getUsername());
        queryWrapper.eq("tpassword",teacherDTO.getPassword());

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
