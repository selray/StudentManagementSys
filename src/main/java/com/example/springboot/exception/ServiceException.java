package com.example.springboot.exception;


import lombok.Getter;

@Getter
//自定义异常
public class ServiceException extends RuntimeException{//继承运行时异常
    private String code;

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;

    }
}
