package com.example.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Lessonchoose;
import com.example.springboot.service.ILessonchooseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/echarts")
public class EchartsController {
    //各种图像数据18节
    @Autowired
    private ILessonchooseService lessonchooseService;
    @GetMapping("/example")
    public Result get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }
    @GetMapping("/members/{studentid}" )
    public Result personScore(@PathVariable String studentid){
        List<Lessonchoose> list = lessonchooseService.list();
        QueryWrapper<Lessonchoose> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("snumber",studentid);
//        return Result.success(lessonchooseService.list());
//        int q1 = 0;
//        int q2 = 0;
//        int q3 = 0;
//        int q4 = 0;
        return Result.success(CollUtil.newArrayList(list));
    }
}
