package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.department;
import com.example.springboot.mapper.departmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends ServiceImpl<departmentMapper, department> {
    public boolean saveDepartment(department dep) {
//        if (dep.getDeptid()==null) {
//            return save(dep);//mybatis_plus提供的方法 新增插入
//        }else {
//            return updateById(dep);
//        }
        return saveOrUpdate(dep);
        //更新成功返回true否则返回false
    }
//
//    public int save(department dep){
//        if(dep.getDeptid()==null){ //没有id则表示新增，否则表示更新
//            return depMatter.insert(dep);
//        }else{//更新
//            return depMatter.update(dep);
//        }

//
//    }
}
