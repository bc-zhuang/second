package com.gk.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.Comment;
import com.gk.study.entity.User;
import com.gk.study.mapper.CommentMapper;
import com.gk.study.mapper.UserMapper;
import com.gk.study.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper mapper;

    private UserMapper userMapper;

    @Override
    public List<Comment> getCommentList() {
        return mapper.getList();
    }

    @Override
    public void createComment(Comment comment) {
        System.out.println(comment);
        comment.setCommentTime(String.valueOf(System.currentTimeMillis()));
        mapper.insert(comment);
    }

    @Override
    public void deleteComment(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void updateComment(Comment comment) {
        mapper.updateById(comment);
    }

    @Override
    public Comment getCommentDetail(String id) {
        return mapper.selectById(id);
    }

    @Override
    public List<Comment> getThingCommentList(String thingId, String order) {
        return mapper.selectThingCommentList(thingId, order);
    }

    @Override
    public List<Comment> getUserCommentList(String userId) {
        return mapper.selectUserCommentList(userId);
    }

    @Override
    public List<Comment> getUserAllCommentsByUserAllThingList(String userId) {

        return mapper.getUserAllCommentsByUserAllThingList(userId);
    }
}
