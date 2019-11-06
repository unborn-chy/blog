package com.scitc.blog.interceptor;
import com.scitc.blog.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=utf-8");
//        Cookie
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    System.out.println("---------------------------------------------------");
                    System.out.println("cookie:" + cookie);
                    System.out.println("cookieValue:" + cookie.getValue());
                    String tokenValue = cookie.getValue();
                    if(tokenValue!=null){
                        boolean result = JwtUtils.verify(tokenValue);
                        if (result) {
                            return true;
                        }
                    }
                }
            }
        }

        //判断头
        String token = request.getHeader("accessToken");
        //token不存在
        if(token!=null){
            boolean result = JwtUtils.verify(token);
            if (result) {
                return true;
            }
        }
            response.sendRedirect("/blog/admin/login");
            return false;
    }
}
