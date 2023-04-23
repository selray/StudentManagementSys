package com.example.springboot.config;


import com.example.springboot.config.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;


//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        // 指定controller统一的接口前缀
//        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")        //拦截所有请求，通过判断token是否合法决定是否需要登录
                .excludePathPatterns("/**/login","/**/register/","/**/export","/**/import","/file/**","http://localhost:9090/lessonchoose/export/");
                //针对登录请求等不需要进行拦截

            //针对某些接口，直接放行

    }

    //@Bean
//    public JwtInterceptor jwtInterceptor(){
//        return new JwtInterceptor();
//    }
}
