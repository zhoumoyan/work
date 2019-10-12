package site.zhongkai.ask.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userid") == null) {
            try {
                response.sendRedirect("/ask/login"); // 重定向
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false; // 仍然需要写return来终止方法
        }
        return true; // 放行
    }

}