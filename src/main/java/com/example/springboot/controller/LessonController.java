package com.example.springboot.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Lesson;
import com.example.springboot.entity.Student;
import com.example.springboot.service.ILessonService;
import com.example.springboot.utils.TokenUtils;
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
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/lesson")
public class LessonController {


    @Resource
    private ILessonService lessonService;

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Lesson lesson){
        //新增或者更新
        return lessonService.saveOrUpdate(lesson);
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return lessonService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return lessonService.removeByIds(ids);
    }


    //查询所有
    @GetMapping
    public List<Lesson> findAll(){
        return lessonService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Lesson findOne(@PathVariable Integer id){
        return lessonService.getById(id);
    }

    //分页查询
    @GetMapping("/page")  //接口路径,多条件查询
    public IPage<Lesson> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String lnumber,
                                       @RequestParam(defaultValue = "") String lname,
                                       @RequestParam(defaultValue = "") String lcredit,
                                       @RequestParam(defaultValue = "") String lcollege){
        IPage<Lesson> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Lesson> queryWrapper = new QueryWrapper<>();
        if(!"".equals(lnumber)){
            queryWrapper.like("lnumber",lnumber);
        }
        if(!"".equals(lname)){
            queryWrapper.like("lname",lname);
        }
        if(!"".equals(lcredit)){
            queryWrapper.like("lcredit",lcredit);
        }
        if(!"".equals(lcollege)){
            queryWrapper.like("lcollege",lcollege);
        }
        //倒序
        //queryWrapper.orderByDesc("id");

        //测试利用token后台获取用户信息,
        Student currentUser = TokenUtils.getCurrentUser();
        //System.out.println("测试获取当前用户信息----------------------"+currentUser.getNativeplace());


        return lessonService.page(page,queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        List<Lesson> list = lessonService.list();
        //从数据库中查询出所有数据
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //在内存操作，写出到浏览器

        //自定义标签别名
//        writer.addHeaderAlias("deptid","学院号");
//        writer.addHeaderAlias("deptname","学院名称");
//        writer.addHeaderAlias("address","学院地址");
//        writer.addHeaderAlias("phonecode","学院号");
      //  System.out.println("测试获取当前用户信息----------------------");
        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("全部课程列表","UTF-8");
        //输出文件名称
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }
}


