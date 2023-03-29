package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.controller.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.IStudentService;
import com.example.springboot.entity.Student;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sel
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/student")
public class StudentController {


    @Resource
    private IStudentService studentService;


    @PostMapping("/login")
    public boolean login(@RequestBody UserDTO userDTO){
        //新增或者更新
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否是空
            return  false;
        }
        return studentService.login(userDTO);
    }
    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Student student){
        //新增或者更新
        return studentService.saveOrUpdate(student);
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return studentService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return studentService.removeByIds(ids);
    }


    //查询所有
    @GetMapping
    public List<Student> findAll(){
        return studentService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Student findOne(@PathVariable Integer id){
        return studentService.getById(id);
    }

    //分页查询
//    @GetMapping("/page")
//    public Page<Student> findPage(@RequestParam Interger pageNum,
//        @RequestParam Interger pageSize){
//        QueryWrapper<student> queryWrapper = new QueryWrapper<>();
//        //queryWrapper.orderByDesc("id");
//        return studentService.page(new Page<>(pageNum,pageSize));
//    }
}

