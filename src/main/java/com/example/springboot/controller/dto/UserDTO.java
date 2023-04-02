package com.example.springboot.controller.dto;


import lombok.Data;

//接受前端登录请求的参数
@Data
public class UserDTO {
    private String username;
    private String password;
    private String studentid;
    private String name;
    private String token;
    private String avatarurl;
}
