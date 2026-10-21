package com.example.lostfound.service;

import com.example.lostfound.entity.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByPostId(Integer postId);
    boolean addComment(Comment comment);
    boolean delComment(Integer commentId, Integer userId);
}
