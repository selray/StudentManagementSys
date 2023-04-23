package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Student;
import com.example.springboot.service.IStudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

//    涉及端口返回值改造，参照15节，可以将所有返回值改造成result，但是前端也要跟着改变

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        //新增或者更新
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否是空
            return  Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto= studentService.login(userDTO);
        //String a = "";
        return Result.success(dto);
    }
    //注册接口
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO){
        //新增或者更新
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否是空
            return  Result.error(Constants.CODE_400,"参数错误");
        }
        return Result.success(studentService.register(userDTO));
    }

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Student student){
        //新增或者更新
        //System.out.println("测试传入student的数据=================="+student.getName()+"  "+student.getAvatarurl());
        return studentService.saveOrUpdate(student);
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return studentService.removeById(id);
    }

    @GetMapping("/studentid/{studentid}")
    public Result findOne(@PathVariable String studentid){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentid",studentid);
        return Result.success(studentService.getOne(queryWrapper));
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

