package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Sclass;
import com.example.springboot.entity.department;
import com.example.springboot.mapper.LessonchooseMapper;
import com.example.springboot.mapper.SclassMapper;
import com.example.springboot.service.ISclassService;
import com.example.springboot.service.impl.SclassServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.springboot.service.ILessonchooseService;
import com.example.springboot.entity.Lessonchoose;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sel
 * @since 2023-04-02
 */
@RestController
@RequestMapping("/lessonchoose")
public class LessonchooseController {


    @Resource
    private ILessonchooseService lessonchooseService;
    @Resource
    private LessonchooseMapper lessonchooseMapper;

    @Resource
    private SclassMapper sclassMapper;
    @Resource
    private ISclassService sclassService;
    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Lessonchoose lessonchoose){
        //新增或者更新
        return lessonchooseService.saveOrUpdate(lessonchoose);
    }

    @GetMapping("/xk")
    public boolean chooseClass(@RequestParam(defaultValue = "") Integer lnumber,
                               @RequestParam(defaultValue = "") Integer tnumber,
                               @RequestParam(defaultValue = "") String semester,
                               @RequestParam(defaultValue = "") Integer snumber){
        System.out.println("========后台接收到传入信息："+lnumber+tnumber+semester+snumber);
        QueryWrapper<Lessonchoose> queryWrapper = new QueryWrapper<>();
        if(!"".equals(lnumber)){
            queryWrapper.eq("lnumber",lnumber);
        }
        if(!"".equals(tnumber)){
            queryWrapper.eq("tnumber",tnumber);
        }
        if(!"".equals(semester)){
            queryWrapper.eq("semester",semester);
        }
        if(!"".equals(snumber)){
            queryWrapper.eq("snumber",snumber);
        }
        //判断查询是否为空
        Page Page=new Page(1,1);
        IPage iPage=lessonchooseMapper.selectPage(Page,queryWrapper);
        List row = iPage.getRecords();
//判断查询结果是否为空
        if(row== null || row.size() ==0){
            //执行选课成功，在lessonchoose表中执行插入
            Lessonchoose lessonchoose = new Lessonchoose();
            lessonchoose.setLnumber(lnumber);
            lessonchoose.setTnumber(tnumber);
            lessonchoose.setSemester(semester);
            lessonchoose.setSnumber(Integer.valueOf(snumber));



            //在class表中添加人数
            QueryWrapper<Sclass> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("tnumber",tnumber);
            //queryWrapper2.eq("snumber",snumber);
            queryWrapper2.eq("lnumber",lnumber);
            queryWrapper2.eq("semester",semester);
            //临时存储原先的数据
            Sclass sclass = sclassMapper.selectOne(queryWrapper2);


            //判断当前人数是否满额
            if(sclass.getCurrentsize()>=sclass.getMaxsize()){
                return false;
            }
            //删除旧的课表信息
            sclassMapper.delete(queryWrapper2);
            sclass.setCurrentsize(sclass.getCurrentsize()+1);
            //人数加一
            sclassService.save(sclass);
            //在lessonchoose表中添加记录
            lessonchooseService.save(lessonchoose);
            return true;
        }else{
            return false;
        }


        //lessonchooseService.saveOrUpdate(lessonchoose);
    }
    @GetMapping("/tk")
    public boolean CancelClass(@RequestParam(defaultValue = "") Integer lnumber,
                               @RequestParam(defaultValue = "") Integer tnumber,
                               @RequestParam(defaultValue = "") String semester,
                               @RequestParam(defaultValue = "") Integer snumber){
        System.out.println("========后台接收到传入信息："+lnumber+tnumber+semester+snumber);
        QueryWrapper<Lessonchoose> queryWrapper = new QueryWrapper<>();
        if(!"".equals(lnumber)){
            queryWrapper.eq("lnumber",lnumber);
        }
        if(!"".equals(tnumber)){
            queryWrapper.eq("tnumber",tnumber);
        }
        if(!"".equals(semester)){
            queryWrapper.eq("semester",semester);
        }
        if(!"".equals(snumber)){
            queryWrapper.eq("snumber",snumber);
        }
        //判断查询是否为空
        Page Page=new Page(1,1);
        IPage iPage=lessonchooseMapper.selectPage(Page,queryWrapper);
        List row = iPage.getRecords();
        //判断查询结果是否为空
        if(row== null || row.size() ==0){
            //没有查到记录，也就没法退课
            return false;
        }else{
            //执行退课操作
            Lessonchoose lessonchoose = new Lessonchoose();
            lessonchoose.setLnumber(lnumber);
            lessonchoose.setTnumber(tnumber);
            lessonchoose.setSemester(semester);
            lessonchoose.setSnumber(Integer.valueOf(snumber));



            //在class表中减少人数
            QueryWrapper<Sclass> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("tnumber",tnumber);
            //queryWrapper2.eq("snumber",snumber);
            queryWrapper2.eq("lnumber",lnumber);
            queryWrapper2.eq("semester",semester);
            //临时存储原先的数据
            Sclass sclass = sclassMapper.selectOne(queryWrapper2);


            //判断当前人数是否为0
            if(sclass.getCurrentsize()<=0){
                return false;
            }
            //删除旧的课表信息
            sclassMapper.delete(queryWrapper2);
            sclass.setCurrentsize(sclass.getCurrentsize()-1);
            //人数加一
            sclassService.save(sclass);
            //在lessonchoose表中添加记录
            lessonchooseMapper.delete(queryWrapper);
            //lessonchooseService.save(lessonchoose);
            return true;

        }


        //lessonchooseService.saveOrUpdate(lessonchoose);
    }
    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return lessonchooseService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return lessonchooseService.removeByIds(ids);
    }


    //查询所有
    @GetMapping
    public List<Lessonchoose> findAll(){
        return lessonchooseService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Lessonchoose findOne(@PathVariable Integer id){
        return lessonchooseService.getById(id);
    }

    //分页查询
//    @GetMapping("/page")
//    public Page<Lessonchoose> findPage(@RequestParam Interger pageNum,
//        @RequestParam Interger pageSize){
//        QueryWrapper<department> queryWrapper = new QueryWrapper<>();
//        //queryWrapper.orderByDesc("id");
//        return lessonchooseService.page(new Page<>(pageNum,pageSize));
//    }
}

