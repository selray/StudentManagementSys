package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.TeacherDTO;
import com.example.springboot.entity.Teacher;
import com.example.springboot.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public Result login(@RequestBody TeacherDTO teacherDTO){
        //新增或者更新
        String username = teacherDTO.getUsername();
        String password = teacherDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否为空
            return  Result.error(Constants.CODE_400,"参数错误");
        }
        TeacherDTO dto= teacherService.login(teacherDTO);
        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody TeacherDTO teacherDTO){//@RequestBody将前台josn对象转换为后台的java对象
        String username= teacherDTO.getUsername();
        String password=teacherDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(teacherService.register(teacherDTO));
    }

    //查询所有
    @GetMapping
    public List<Teacher> findAll(){
        return teacherService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Teacher findeOne(@PathVariable Integer id){
        return teacherService.getById(id);
    }

    @GetMapping("/id/{id}")
    public Result findOne(@PathVariable Integer id){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Tnumber",id);
        Teacher teacher = teacherService.getOne(queryWrapper);
        return Result.success(teacher);
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

