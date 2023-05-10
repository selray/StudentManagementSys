package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.config.interceptor.MD5TypeHandler;
import com.example.springboot.controller.dto.ManagerDTO;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Manager;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.Teacher;
import com.example.springboot.mapper.ManagerMapper;
import com.example.springboot.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/manager")
public class ManagerController {

@Autowired
    ManagerMapper managerMapper;
    @Resource
    private IManagerService managerService;

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Manager manager){
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Mnumber", manager.getMnumber());
        Manager objectmanager=managerMapper.selectOne(queryWrapper);
        objectmanager.setMusername(manager.getMusername());
        if(manager.getMpassword().equals(objectmanager.getMpassword()))
        {
            objectmanager.setMpassword(manager.getMpassword());
        }
        else objectmanager.setPassword(manager.getMpassword());
        //新增或者更新
        return managerService.saveOrUpdate(objectmanager);
    }


    @PostMapping("/login")
    public Result login(@RequestBody ManagerDTO managerDTO){
        //新增或者更新
        String username = managerDTO.getUsername();
        String password = managerDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否是空
            return  Result.error(Constants.CODE_400,"参数错误");
        }
        String encryptedPassword = encryptPassword(password);
        managerDTO.setPassword(encryptedPassword);
        ManagerDTO dto= managerService.login(managerDTO);
        return Result.success(dto);
    }public String encryptPassword(String password) {
        MD5TypeHandler md5TypeHandler = new MD5TypeHandler();
        return md5TypeHandler.md5(password);
    }
    //删除
    @DeleteMapping("/{id}")
    public boolean  delete(@PathVariable Integer id){
        return managerService.removeById(id);
    }


    @PostMapping("/del/batch")
    public boolean  deleteBatch(@RequestBody List<Integer> ids){
        return managerService.removeByIds(ids);
    }


    //查询所有
    @GetMapping
    public List<Manager> findAll(){
        return managerService.list();
    }
    //根据id查询
    @GetMapping("/{id}")
    public Manager findOne(@PathVariable Integer id){
        return managerService.getById(id);
    }

    @GetMapping("/mnumber/{mnumber}")
    public Result findeOne(@PathVariable String  mnumber){
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Mnumber",mnumber);
        Manager manager = managerService.getOne(queryWrapper);
        return Result.success(manager);
    }
}

