package com.example.lostfound.controller;

import com.example.lostfound.common.Result;
import com.example.lostfound.entity.User;
import com.example.lostfound.service.UserService;
import com.example.lostfound.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    // 用户登录：校验账号密码，成功后签发token
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            // 生成token
            String token = JwtUtil.createToken(loginUser.getUserId(), loginUser.getUsername());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userInfo", loginUser);
            return Result.success("登录成功", data);
        }
        return Result.error(401, "用户名或密码错误");
    }

    // 用户注册
    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        boolean success = userService.register(user);
        if (success) {
            return Result.success("注册成功", null);
        }
        return Result.error(400, "用户名已存在");
    }

    // 根据id查询用户信息
    @GetMapping("/{userId}")
    public Result<User> getById(@PathVariable Integer userId) {
        User user = userService.getById(userId);
        if (user != null) {
            return Result.success("查询成功", user);
        }
        return Result.error(404, "用户不存在");
    }
}
