package com.example.springboot.controller.dto;


import com.baomidou.mybatisplus.annotation.TableField;
import com.example.springboot.config.interceptor.MD5TypeHandler;
import lombok.Data;

//接受前端登录请求的参数
@Data
public class UserDTO {
    private String username;
    @TableField(value = "password", typeHandler = MD5TypeHandler.class)
    private String password;
    private String studentid;
    private String name;
    private String token;
    private String avatarurl;

}
