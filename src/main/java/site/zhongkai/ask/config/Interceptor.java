package site.zhongkai.ask.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.zhongkai.ask.interceptor.LoginInterceptor;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class Interceptor implements WebMvcConfigurer {

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        //放行的白名单
        List<String> list = new ArrayList<>();
        list.add("/ask/**");
        /*list.add("/ask/css/**");
        list.add("/ask/fonts/**");
        list.add("/ask/ico/**");
        list.add("/ask/img/**");
        list.add("/ask/js/**");
        list.add("/ask/layui/**");
        list.add("/ask/html/**");
        list.add("/ask/login.html");
        list.add("/ask/index.html");
        list.add("/ask/answer/**");
        list.add("/ask/manager/login");
        */
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(list);
        //WebMvcConfigurer.super.addInterceptors(registry);
    }

}