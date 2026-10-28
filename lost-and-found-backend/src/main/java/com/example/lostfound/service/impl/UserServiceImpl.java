package com.example.lostfound.service.impl;

import com.example.lostfound.entity.User;
import com.example.lostfound.mapper.UserMapper;
import com.example.lostfound.service.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(password)) {
            return null;
        }
        // 登录成功不返回密码
        user.setPassword(null);
        return user;
    }

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        User exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            return false;
        }
        // 默认注册为普通用户
        if (user.getRole() == null) {
            user.setRole(0);
        }
        return userMapper.insert(user) > 0;
    }

    @Override
    public User getById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }

    @Override
    public List<User> listAll() {
        List<User> list = userMapper.selectAll();
        if (list != null) {
            for (User u : list) {
                u.setPassword(null); // 隐藏密码
            }
        }
        return list;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        return userMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean updateRole(Integer userId, Integer role) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            return false;
        }
        user.setRole(role);
        return userMapper.update(user) > 0;
    }
}
