package com.example.lostfound.controller;

import com.example.lostfound.common.Result;
import com.example.lostfound.entity.Post;
import com.example.lostfound.service.PostService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    // 查询帖子列表（可按类型筛选：0寻物 1招领）
    @GetMapping("/list")
    public Result<List<Post>> list(@RequestParam(required = false) Integer type) {
        return Result.success("查询成功", postService.list(type));
    }

    // 查询某用户发布的所有帖子
    @GetMapping("/my/{userId}")
    public Result<List<Post>> myPosts(@PathVariable Integer userId) {
        return Result.success("查询成功", postService.myPosts(userId));
    }

    // 根据id查询帖子详情
    @GetMapping("/{postId}")
    public Result<Post> getById(@PathVariable Integer postId) {
        Post post = postService.getById(postId);
        if (post != null) {
            return Result.success("查询成功", post);
        }
        return Result.error(404, "帖子不存在");
    }

    // 发布帖子
    @PostMapping("/add")
    public Result<Void> add(@RequestBody Post post) {
        boolean success = postService.add(post);
        return success ? Result.success("发布成功", null) : Result.error(500, "发布失败");
    }

    // 修改帖子
    @PutMapping("/update")
    public Result<Void> update(@RequestBody Post post) {
        boolean success = postService.update(post);
        return success ? Result.success("修改成功", null) : Result.error(500, "修改失败");
    }

    // 认领物品（帖子状态置为已完成）
    @PutMapping("/claim/{postId}")
    public Result<Void> claim(@PathVariable Integer postId) {
        boolean success = postService.claim(postId);
        return success ? Result.success("认领成功", null) : Result.error(500, "操作失败");
    }

    // 删除帖子
    @DeleteMapping("/{postId}")
    public Result<Void> delete(@PathVariable Integer postId) {
        boolean success = postService.delete(postId);
        return success ? Result.success("删除成功", null) : Result.error(500, "删除失败");
    }
}
