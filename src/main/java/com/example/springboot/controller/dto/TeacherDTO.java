package com.example.springboot.controller.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.springboot.config.interceptor.MD5TypeHandler;
import lombok.Data;

@Data
public class TeacherDTO {
    private Integer tnumber;
    private String username;
    @TableField(value = "password", typeHandler = MD5TypeHandler.class)
    private String password;
    private String name;
    private String avatarurl;
    private String token;
}
