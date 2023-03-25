package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "student")
public class student {
    private String stu_id;
    private String stu_name;
    private String stu_username;
    private String stu_passwd;


}
