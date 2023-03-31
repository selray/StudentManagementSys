package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.ITeacherService;
import com.example.springboot.entity.Teacher;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sel
 * @since 2023-03-31
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Resource
    private ITeacherService teacherService;

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Teacher teacher){
        //新增或者更新
        return teacherService.saveOrUpdate(teacher);
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return teacherService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return teacherService.removeByIds(ids);
    }


    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        //新增或者更新
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否是空
            return  Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto= teacherService.login(userDTO);
        return Result.success(dto);
    }

    //查询所有
    @GetMapping
    public List<Teacher> findAll(){
        return teacherService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Teacher findOne(@PathVariable Integer id){
        return teacherService.getById(id);
    }

    //分页查询
//    @GetMapping("/page")
//    public Page<Teacher> findPage(@RequestParam Interger pageNum,
//        @RequestParam Interger pageSize){
//        QueryWrapper<department> queryWrapper = new QueryWrapper<>();
//        //queryWrapper.orderByDesc("id");
//        return teacherService.page(new Page<>(pageNum,pageSize));
//    }
}

