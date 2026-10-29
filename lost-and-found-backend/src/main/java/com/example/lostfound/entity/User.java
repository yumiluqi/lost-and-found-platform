package com.example.lostfound.entity;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String phone;
    private Integer role;   // 角色：0普通用户 1管理员
    private Date createTime;
}
