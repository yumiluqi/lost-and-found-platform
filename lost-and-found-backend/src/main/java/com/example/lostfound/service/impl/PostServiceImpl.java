package com.example.lostfound.service.impl;

import com.example.lostfound.entity.Post;
import com.example.lostfound.mapper.PostMapper;
import com.example.lostfound.service.PostService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    @Override
    public List<Post> list(Integer type) {
        return postMapper.selectList(type);
    }

    @Override
    public Post getById(Integer postId) {
        return postMapper.selectById(postId);
    }

    @Override
    public List<Post> myPosts(Integer userId) {
        return postMapper.selectByUserId(userId);
    }

    @Override
    public boolean add(Post post) {
        post.setCreateTime(new Date());
        if (post.getStatus() == null) {
            post.setStatus(0);
        }
        return postMapper.insert(post) > 0;
    }

    @Override
    public boolean update(Post post) {
        return postMapper.update(post) > 0;
    }

    @Override
    public boolean claim(Integer postId) {
        // 0=进行中 -> 1=已完成（认领成功）
        return postMapper.updateStatus(postId, 1) > 0;
    }

    @Override
    public boolean delete(Integer postId) {
        return postMapper.deleteById(postId) > 0;
    }
}
