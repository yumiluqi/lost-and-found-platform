package com.example.lostfound.interceptor;

import com.example.lostfound.common.Result;
import com.example.lostfound.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器：校验请求头里的token
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行OPTIONS预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            returnJson(response, 401, "未登录，请先登录");
            return false;
        }

        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            returnJson(response, 401, "登录已过期，请重新登录");
            return false;
        }

        // 把用户ID存到request里，Controller可以直接取
        request.setAttribute("userId", claims.get("userId"));
        return true;
    }

    private void returnJson(HttpServletResponse response, int code, String msg) throws Exception {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        Result<Object> result = Result.error(code, msg);
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
