package com.example.lostfound.service.impl;

import com.example.lostfound.entity.Comment;
import com.example.lostfound.mapper.CommentMapper;
import com.example.lostfound.service.CommentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentMapper.selectByPostId(postId);
    }

    @Override
    public boolean addComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public boolean delComment(Integer commentId, Integer userId) {
        // 权限校验：只能删自己的评论
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            return false;
        }
        if (!comment.getUserId().equals(userId)) {
            return false; // 不是自己的评论，无权删除
        }
        return commentMapper.deleteById(commentId) > 0;
    }
}
