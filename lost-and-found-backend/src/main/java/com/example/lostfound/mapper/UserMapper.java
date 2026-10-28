package com.example.lostfound.mapper;

import com.example.lostfound.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    User selectByUsername(String username);
    User selectById(Integer userId);
    int insert(User user);
    int update(User user);
    List<User> selectAll();
    int deleteById(Integer userId);
}
