package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.department;
import com.example.springboot.mapper.departmentMapper;
import com.example.springboot.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
//初始url,要和下面拼接形成完整url
public class DepartmentController {
    @Autowired
    private DepartmentService depService;

    //增加
    @PostMapping
    public boolean save(@RequestBody department dep){
        //新增或者更新
        return depService.saveDepartment(dep);
    }
    //    查询所有数据
    @GetMapping
    public List<department> findAll(){
        return depService.list();
    }
    //    删除
    @GetMapping("/{id}")
    public List<department> findOne(@PathVariable Integer id){
        return depService.list();
    }
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return depService.removeById(id);
    }
//    批量删除接口
    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return depService.removeByIds(ids);
    }

    //分页查询
//    @GetMapping("/page")  //接口路径
//    public Map<String, Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam String deptname){
//        pageNum = (pageNum -1 ) * pageSize;
//        deptname = "%" + deptname + "%";
//        List<department> data = dep_matter.selectPage(pageNum,pageSize,deptname);
//        Integer total = dep_matter.selectTotal(deptname);
//        Map<String,Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }
    //mybatis plus分页查询
    @GetMapping("/page")  //接口路径,多条件查询
    public IPage<department > findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                       @RequestParam(defaultValue = "") String deptid,
                                        @RequestParam(defaultValue = "") String deptname,
                                       @RequestParam(defaultValue = "") String address,
                                       @RequestParam(defaultValue = "") String phonecode){
        IPage<department> page = new Page<>(pageNum,pageSize);
        QueryWrapper<department> queryWrapper = new QueryWrapper<>();
        if(!"".equals(deptid)){
            queryWrapper.like("deptid",deptid);
        }
        if(!"".equals(deptname)){
            queryWrapper.like("deptname",deptname);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }
        if(!"".equals(phonecode)){
            queryWrapper.like("phonecode",phonecode);
        }
        //倒序
        //queryWrapper.orderByDesc("id");


        return depService.page(page,queryWrapper);
    }
}

