package com.example.lostfound.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Post {
    private Integer postId;
    private Integer userId;
    private Integer type;       // 0=lost寻物 1=found招领
    private String title;
    private String goodsType;   // 物品类型
    private String location;    // 地点
    private String description; // 描述
    private String imgUrl;
    private Integer status;     // 0=进行中 1=已完成
    private Date createTime;
}
