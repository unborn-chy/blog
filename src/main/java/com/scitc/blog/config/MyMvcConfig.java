package com.scitc.blog.config;
import com.scitc.blog.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().contains("win")){
            registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/photo/upload/");
        }else {
            registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/chy/image/upload/");
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/AdminLTE-2.4.13/**","/css/**","/img/**","/js/**","/layui/**","404.html",
                        "/admin/login","/manage/login","/admin/register","/manage/register","/admin/findpsw","/manage/findpassword",
                        "/upload/**","/frontend/**","/index","/tag/**","/category/**","/article/**");
    }
}
