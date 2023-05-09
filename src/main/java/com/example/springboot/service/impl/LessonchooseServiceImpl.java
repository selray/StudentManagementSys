package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Lessonchoose;
import com.example.springboot.entity.Student;
import com.example.springboot.mapper.LessonchooseMapper;
import com.example.springboot.service.ILessonchooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sel
 * @since 2023-04-02
 */
@Service
public class LessonchooseServiceImpl extends ServiceImpl<LessonchooseMapper, Lessonchoose> implements ILessonchooseService {

    @Resource
    private LessonchooseMapper lessonchooseMapper;

    @Override
    public Page<Student> getClassStudents(Page<Object> page, Integer tnumber, Integer lnumber) {
        return lessonchooseMapper.getClassStudents(page,tnumber,lnumber);
    }

    @Override
    public List<Student> listClassStudents(Integer tnumber, Integer lnumber) {
        return lessonchooseMapper.listClassStudents(tnumber,lnumber);
    }
}
