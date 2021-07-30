package com.example.demo.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    // 自定义拦截方法，返回结果是 boolean，当
    // 为 true 表示可以方法后端接口，为 false 表示无权访问后端接口
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 判断 session 是否有值
        HttpSession session = request.getSession(false);
        if (session != null &&
                session.getAttribute(AppFinal.USERINFO_SESSIONKEY) != null) {
            // 用户已经登录
            return true;
        }
        return false;
    }
}


package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", c -> true);
    }

    // 配置拦截规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // 拦截所有的接口
                .excludePathPatterns("/api/user/login") // 不拦截登录接口
                .excludePathPatterns("/api/user/reg") // 不拦截注册接口
                .excludePathPatterns("/login.html") // 不拦截登录页面
                .excludePathPatterns("/regin.html") // 不拦截注册页面
                .excludePathPatterns("/**/**.html") // 不拦截注册页面
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.jpg")
                .excludePathPatterns("/reg_success.html")
                .excludePathPatterns("/reg_err.html")
                .excludePathPatterns("/**/*.png");
    }
}