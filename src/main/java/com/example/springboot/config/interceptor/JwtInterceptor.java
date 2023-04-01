package com.example.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.common.Constants;
import com.example.springboot.entity.Manager;
import com.example.springboot.entity.Student;
import com.example.springboot.entity.Teacher;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.IManagerService;
import com.example.springboot.service.IStudentService;
import com.example.springboot.service.ITeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {


    //拦截器
    @Autowired
    private IStudentService studentService;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IManagerService managerService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        //如果不是映射方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if (StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //获取token中的userid
        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        // 根据token中的userID 查询数据库
        Student student = studentService.getById(userId);
        Teacher teacher = teacherService.getById(userId);
        Manager manager = managerService.getById(userId);

        if(student == null&&teacher == null&&manager == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        if (student!=null){
            JWTVerifier jwtVerifier1 = JWT.require(Algorithm.HMAC256(student.getPswd())).build();
            try{
                jwtVerifier1.verify(token); //验证token
            } catch (JWTVerificationException e1){

                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
        } else if (teacher != null) {
            JWTVerifier jwtVerifier2 = JWT.require(Algorithm.HMAC256(teacher.getTpassword())).build();
            try{
                jwtVerifier2.verify(token); //验证token
            } catch (JWTVerificationException e2){

                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
        }else if (manager != null){
            JWTVerifier jwtVerifier3 = JWT.require(Algorithm.HMAC256(manager.getMpassword())).build();
            try{
                jwtVerifier3.verify(token); //验证token
            } catch (JWTVerificationException e3){
                throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
            }
        }
        //用户密码加签验证token


        return true;
    }


}
