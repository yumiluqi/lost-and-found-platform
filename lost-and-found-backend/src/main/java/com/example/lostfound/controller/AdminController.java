package com.example.lostfound.controller;

import com.example.lostfound.common.Result;
import com.example.lostfound.entity.Comment;
import com.example.lostfound.entity.Post;
import com.example.lostfound.entity.User;
import com.example.lostfound.mapper.CommentMapper;
import com.example.lostfound.mapper.PostMapper;
import com.example.lostfound.service.CommentService;
import com.example.lostfound.service.PostService;
import com.example.lostfound.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员后台接口：仅管理员（role=1）可访问，已被 AdminInterceptor 拦截保护
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private CommentService commentService;
    @Resource
    private PostMapper postMapper;
    @Resource
    private CommentMapper commentMapper;

    // ========== 用户管理 ==========

    /** 获取所有用户列表 */
    @GetMapping("/users")
    public Result<List<User>> listUsers() {
        return Result.success("查询成功", userService.listAll());
    }

    /** 删除用户（连带删除其帖子、评论） */
    @DeleteMapping("/user/{userId}")
    public Result<Void> deleteUser(@PathVariable Integer userId) {
        // 先删该用户发布的帖子及帖子下的评论
        List<Post> posts = postService.myPosts(userId);
        if (posts != null) {
            for (Post p : posts) {
                commentMapper.deleteByPostId(p.getPostId());
            }
        }
        postMapper.deleteByUserId(userId);
        commentMapper.deleteByUserId(userId);
        boolean ok = userService.deleteUser(userId);
        return ok ? Result.success("删除用户成功", null) : Result.error(500, "删除用户失败");
    }

    /** 设置用户角色：0普通用户 1管理员 */
    @PutMapping("/user/{userId}/role")
    public Result<Void> updateRole(@PathVariable Integer userId, @RequestParam Integer role) {
        boolean ok = userService.updateRole(userId, role);
        return ok ? Result.success("角色设置成功", null) : Result.error(500, "设置失败");
    }

    // ========== 帖子管理 ==========

    /** 获取所有帖子 */
    @GetMapping("/posts")
    public Result<List<Post>> listPosts() {
        return Result.success("查询成功", postService.list(null));
    }

    /** 删除任意帖子（连带删除其评论） */
    @DeleteMapping("/post/{postId}")
    public Result<Void> deletePost(@PathVariable Integer postId) {
        commentMapper.deleteByPostId(postId);
        boolean ok = postService.delete(postId);
        return ok ? Result.success("删除帖子成功", null) : Result.error(500, "删除失败");
    }

    // ========== 评论管理 ==========

    /** 获取某个帖子下的全部评论 */
    @GetMapping("/post/{postId}/comments")
    public Result<List<Comment>> listComments(@PathVariable Integer postId) {
        return Result.success("查询成功", commentService.getCommentsByPostId(postId));
    }

    /** 删除任意评论 */
    @DeleteMapping("/comment/{commentId}")
    public Result<Void> deleteComment(@PathVariable Integer commentId) {
        // 管理员不受"只能删自己的"限制，直接删
        boolean ok = commentMapper.deleteById(commentId) > 0;
        return ok ? Result.success("删除评论成功", null) : Result.error(500, "删除失败");
    }
}
