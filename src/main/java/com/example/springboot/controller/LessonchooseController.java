package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Lessonchoose lessonchoose){
        //新增或者更新
        return lessonchooseService.saveOrUpdate(lessonchoose);
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

