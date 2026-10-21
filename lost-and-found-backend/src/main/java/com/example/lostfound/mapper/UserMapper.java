package com.example.lostfound.mapper;

import com.example.lostfound.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectByUsername(String username);
    User selectById(Integer userId);
    int insert(User user);
    int update(User user);
}
