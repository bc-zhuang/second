package com.gk.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("b_comment")
public class Comment implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String content;
    @TableField
    public String commentTime;
    @TableField
    public String likeCount;
    @TableField
    public String userId;
    @TableField(exist = false)
    public String username; // 用户名
    @TableField
    public String thingId; // 商品id
    @TableField(exist = false)
    public String title; // 商品名称
    @TableField(exist = false)
    public String cover; // 商品封面
    @TableField(exist = false)
    public String nickname; // 用户昵称
    @TableField(exist = false)
    public String b_user_id; // 用户id
    @TableField(exist = false)
    public String avatar; // 用户头像

    public Thing thing;
    public User user;
}
