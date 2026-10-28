package com.example.lostfound.interceptor;

import com.example.lostfound.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员权限拦截器：只有管理员（role=1）才能访问 /admin/** 接口
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行OPTIONS预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 取出LoginInterceptor放进去的role
        Object role = request.getAttribute("role");
        if (role == null || !"1".equals(role.toString())) {
            response.setStatus(200);
            response.setContentType("application/json;charset=UTF-8");
            Result<Object> result = Result.error(403, "无管理员权限");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
            return false;
        }
        return true;
    }
}
