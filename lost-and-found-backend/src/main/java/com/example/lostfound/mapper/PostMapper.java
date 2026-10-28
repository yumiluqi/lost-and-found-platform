package com.example.lostfound.mapper;

import com.example.lostfound.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> selectList(@Param("type") Integer type);
    Post selectById(Integer postId);
    List<Post> selectByUserId(Integer userId);
    int insert(Post post);
    int update(Post post);
    int updateStatus(@Param("postId") Integer postId, @Param("status") Integer status);
    int deleteById(Integer postId);
}
