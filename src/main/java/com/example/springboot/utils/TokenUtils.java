package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.Teacher;
import com.example.springboot.service.IStudentService;
import com.example.springboot.service.ITeacherService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
//生成token

@Component
public class TokenUtils {


    private static IStudentService staticstudentService;
    @Resource
    private IStudentService studentService;

    private static ITeacherService staticTeacherService;
    @Resource
    private ITeacherService teacherService;

//    后台项目启动的时候
    @PostConstruct
    public void setStudentService(){
        staticstudentService = studentService;
    }

    @PostConstruct
    public void setTeacherService(){
        staticTeacherService=teacherService;
    }


    public static String genToken(String userId, String sign) {
        return JWT.create().withAudience(userId) //将userid保存到token里面。作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                .sign(Algorithm.HMAC256(sign));
    }

    //后台通过这种静态方法，从token可以获取当前用户的信息
    public static Student getCurrentUser(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String studentId = JWT.decode(token).getAudience().get(0);
                return staticstudentService.getById(Integer.valueOf(studentId));
            }
        }catch (Exception e){
            return null;
        }
          return null;
    }

    public static Teacher getCurrentTeacher(){
        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String userId=JWT.decode(token).getAudience().get(0);
                return staticTeacherService.getById(Integer.valueOf(userId));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}


