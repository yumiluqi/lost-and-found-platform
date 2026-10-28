package com.example.lostfound.mapper;

import com.example.lostfound.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> selectByPostId(Integer postId);
    Comment selectById(Integer commentId);
    int insert(Comment comment);
    int deleteById(Integer commentId);
}
