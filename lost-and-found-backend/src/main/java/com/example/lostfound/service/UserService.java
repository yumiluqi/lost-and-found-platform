package com.example.lostfound.service;

import com.example.lostfound.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    boolean register(User user);
    User getById(Integer userId);
    List<User> listAll();
    boolean deleteUser(Integer userId);
    boolean updateRole(Integer userId, Integer role);
}
