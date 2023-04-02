package com.example.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import com.example.springboot.common.Result;
import com.example.springboot.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/echarts")
public class EchartsController {

//    @Autowired
//    private IStudentService
    @GetMapping("/example")
    public Result get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }
//    @GetMapping("/members")
//    public Result menbers(){
//
//    }
}
