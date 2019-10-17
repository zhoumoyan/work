package site.zhongkai.ask.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.zhongkai.ask.interceptor.LoginInterceptor;

import java.util.ArrayList;
import java.util.List;

// 拦截器
@Configuration
public class Interceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> list = new ArrayList<>();
        list.add("/manage/handle_login");
        list.add("/manage/logout");
        list.add("/wx_user/**");
        list.add("/portal/**");
        list.add("/answer/**");
        list.add("/css/**");
        list.add("/down/**");
        list.add("/fonts/**");
        list.add("/ico/**");
        list.add("/img/**");
        list.add("/js/**");
        list.add("/layui/**");
        list.add("/login.html");
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(list);
        //WebMvcConfigurer.super.addInterceptors(registry);
    }

}