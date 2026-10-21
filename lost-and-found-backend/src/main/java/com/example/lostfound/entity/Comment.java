package com.example.lostfound.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Comment {
    private Integer commentId;
    private Integer postId;
    private Integer userId;
    private String content;
    private Date createTime;
}
