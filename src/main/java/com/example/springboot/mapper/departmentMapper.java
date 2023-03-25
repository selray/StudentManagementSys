package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.department;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface departmentMapper extends BaseMapper<department> {
//    @Select("select * from department where deptname like #{deptname} limit #{pageNum},#{pageSize}")
//    List<department> selectPage(Integer pageNum, Integer pageSize,String deptname);
//
//    @Select("select * from department")
//    List<department> findAll();
//
//    @Insert("INSERT into department(deptid,deptname,address,phonecode) " +
//            "value(#{deptid},#{deptname},#{address},#{phonecode})")
//    int insert(department dep);
//
//
//    int update(department dep);
//
//    @Delete("delete from department where deptid = #{deptid}")
//    Integer deleteById(@Param("deptid") Integer id);
//
//    @Select("select count(*) from department where deptname like #{deptname}")
//    Integer selectTotal(String deptname);
}
