package com.example.lostfound.config;

import com.example.lostfound.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置：注册拦截器 + 静态资源映射（图片访问）
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /** 拦截器配置：哪些接口需要登录 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/user/login",      // 登录
                        "/user/register",   // 注册
                        "/post/list",       // 帖子列表（游客可看）
                        "/comment/list",    // 评论列表（游客可看）
                        "/upload/**",       // 上传的图片
                        "/error"
                );
    }

    /** 静态资源映射：上传的图片可以通过URL访问，和UploadController使用完全一致的动态路径，避免相对路径找不到文件 */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = System.getProperty("user.dir") + "/upload/";
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadPath);
    }
}
