package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.Manager;
import com.example.springboot.service.IManagerService;
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


    @Resource
    private IManagerService managerService;

    //新增或更新
    @PostMapping
    public boolean save(@RequestBody Manager manager){
        //新增或者更新
        return managerService.saveOrUpdate(manager);
    }


    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        //新增或者更新
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            //校验字符串是否是空
            return  Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto= managerService.login(userDTO);
        return Result.success(dto);
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

    //分页查询
//    @GetMapping("/page")
//    public Page<Manager> findPage(@RequestParam Interger pageNum,
//        @RequestParam Interger pageSize){
//        QueryWrapper<department> queryWrapper = new QueryWrapper<>();
//        //queryWrapper.orderByDesc("id");
//        return managerService.page(new Page<>(pageNum,pageSize));
//    }
}

