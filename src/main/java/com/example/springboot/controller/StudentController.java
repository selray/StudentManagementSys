package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.config.interceptor.MD5TypeHandler;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Lesson;
import com.example.springboot.entity.Lessonchoose;
import com.example.springboot.entity.Manager;
import com.example.springboot.entity.Student;
import com.example.springboot.mapper.StudentMapper;
import com.example.springboot.service.ILessonchooseService;
import com.example.springboot.service.IStudentService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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
@Autowired
private StudentMapper studentmapper;
    @Resource
    private IStudentService studentService;
    @Resource
    private ILessonchooseService lessonchooseService;

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
        String encryptedPassword = encryptPassword(password);
        userDTO.setPassword(encryptedPassword);
        UserDTO dto= studentService.login(userDTO);
        //String a = "";
        return Result.success(dto);
    }public String encryptPassword(String password) {
        MD5TypeHandler md5TypeHandler = new MD5TypeHandler();
        return md5TypeHandler.md5(password);
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
//    @DeleteMapping("/{id}")
//    public boolean  delete(@PathVariable Integer id){
//        return studentService.removeById(id);
//    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentmapper.deleteStudent(id);
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

    @GetMapping("/page")
    public IPage<Student> findPage(
            @RequestParam Integer pageNum, @RequestParam Integer pageSize,
            @RequestParam (defaultValue = "")String studentid,
            @RequestParam (defaultValue = "")String name
    ){
        IPage<Student> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(!"".equals(studentid)){
            queryWrapper.like("studentid",studentid);
        }
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        return studentService.page(page,queryWrapper);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Student> list = studentService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("全部学生信息","UTF-8");
        //输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }
}

