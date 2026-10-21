package com.example.lostfound.service;

import com.example.lostfound.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> list(Integer type);
    Post getById(Integer postId);
    List<Post> myPosts(Integer userId);
    boolean add(Post post);
    boolean update(Post post);
    boolean claim(Integer postId);
    boolean delete(Integer postId);
}
