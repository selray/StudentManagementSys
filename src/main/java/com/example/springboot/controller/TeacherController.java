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
import com.example.springboot.controller.dto.TeacherDTO;
import com.example.springboot.entity.Sclass;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.Teacher;
import com.example.springboot.mapper.StudentMapper;
import com.example.springboot.mapper.TeacherMapper;
import com.example.springboot.service.ITeacherService;
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
 * @since 2023-03-31
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherMapper teacherMapper;

    @Resource
    private ITeacherService teacherService;

    //新增或更新
    @PostMapping("/update")
    public boolean save(@RequestBody Teacher teacher){
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tnumber", teacher.getTnumber());
        Teacher objectteacher=teacherMapper.selectOne(queryWrapper);
        objectteacher.setTnumber(teacher.getTnumber());
        objectteacher.setTname(teacher.getTname());
        objectteacher.setTsex(teacher.getTsex());
        objectteacher.setTwage(teacher.getTwage());
        objectteacher.setTusername(teacher.getTusername());
        if(teacher.getTpassword().equals(objectteacher.getTpassword()))
        {
            objectteacher.setTpassword(teacher.getTpassword());
        }
        else objectteacher.setPassword(teacher.getTpassword());



        return teacherService.saveOrUpdate(objectteacher);
    }
    @PostMapping("/save")
    public boolean update(@RequestBody Teacher teacher){
        teacher.setTpassword(encryptPassword(teacher.getTpassword()));
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
        String encryptedPassword = encryptPassword(password);
        teacherDTO.setPassword(encryptedPassword);
        TeacherDTO dto= teacherService.login(teacherDTO);
        return Result.success(dto);
    }public String encryptPassword(String password) {
        MD5TypeHandler md5TypeHandler = new MD5TypeHandler();
        return md5TypeHandler.md5(password);
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

    @GetMapping("/page")
    public IPage<Teacher> findPage(
            @RequestParam Integer pageNum, @RequestParam Integer pageSize,
            @RequestParam (defaultValue = "")String tnumber,
            @RequestParam (defaultValue = "")String tname
    ){
        IPage<Teacher> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(!"".equals(tnumber)){
            queryWrapper.like("tnumber",tnumber);
        }
        if(!"".equals(tname)){
            queryWrapper.like("tname",tname);
        }
        return teacherService.page(page,queryWrapper);
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Teacher> list = teacherService.list();
        //从数据库中查询出所有数据
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //在内存操作，写出到浏览器

        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("教师表格信息","UTF-8");
        //输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }
}

