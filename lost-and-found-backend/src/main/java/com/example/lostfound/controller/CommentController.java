package com.example.lostfound.controller;

import com.example.lostfound.common.Result;
import com.example.lostfound.entity.Comment;
import com.example.lostfound.service.CommentService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/list")
    public Result<List<Comment>> list(@RequestParam Integer postId) {
        return Result.success("查询成功", commentService.getCommentsByPostId(postId));
    }

    @PostMapping("/add")
    public Result<Void> add(@RequestBody Comment comment, HttpServletRequest request) {
        // 从拦截器里取出当前登录用户ID
        Integer userId = (Integer) request.getAttribute("userId");
        comment.setUserId(userId);
        boolean res = commentService.addComment(comment);
        return res ? Result.success("评论成功", null) : Result.error(500, "评论失败");
    }

    @DeleteMapping("/{commentId}")
    public Result<Void> delete(@PathVariable Integer commentId, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        boolean res = commentService.delComment(commentId, userId);
        if (res) {
            return Result.success("删除成功", null);
        }
        return Result.error(403, "只能删除自己的评论");
    }
}
