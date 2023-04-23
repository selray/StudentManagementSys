package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@TableName(value = "department")
@ToString
public class department {
    @TableId(type = IdType.AUTO)
    //@TableField(value = "dept_id") //因为名称不一致可以用这个形式
    private String deptid; //主键需要tableid
    private String deptname;
    private String address;
    private String phonecode;


}
