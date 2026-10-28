package com.example.lostfound.service;

import com.example.lostfound.entity.User;

public interface UserService {
    User login(String username, String password);
    boolean register(User user);
    User getById(Integer userId);
}
