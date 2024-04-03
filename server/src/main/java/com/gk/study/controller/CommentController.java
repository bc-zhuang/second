package com.gk.study.controller;

import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.config.ExcelUtils;
import com.gk.study.entity.Comment;
import com.gk.study.entity.Order;
import com.gk.study.filter.WordContext;
import com.gk.study.filter.WordFilter;
import com.gk.study.permission.Access;
import com.gk.study.permission.AccessLevel;
import com.gk.study.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public APIResponse list(){
        List<Comment> list =  service.getCommentList();
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }


    // 商品的所有评论
    @RequestMapping(value = "/listThingComments", method = RequestMethod.GET)
    public APIResponse listThingComments(String thingId, String order){
        List<Comment> list =  service.getThingCommentList(thingId, order);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    // 用户的所有评论
    @RequestMapping(value = "/listUserComments", method = RequestMethod.GET)
    public APIResponse listUserComments(String userId){
        List<Comment> list =  service.getUserCommentList(userId);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public APIResponse create(Comment comment) throws IOException {


        WordContext wordContext = new WordContext();
        WordFilter filter = new WordFilter(wordContext);
        String encodeComment = filter.replace(comment.getContent());
        comment.setContent(encodeComment);


        service.createComment(comment);
        return new APIResponse(ResponeCode.SUCCESS, "创建成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public APIResponse delete(String ids){
        System.out.println("ids===" + ids);
        // 批量删除
        String[] arr = ids.split(",");
        for (String id : arr) {
            service.deleteComment(id);
        }
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }

    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public APIResponse deleteOne(String id){
        System.out.println("id===" + id);
        service.deleteComment(id);
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }

    @Access(level = AccessLevel.ADMIN)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public APIResponse update(Comment comment) throws IOException {
        service.updateComment(comment);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @Transactional
    public APIResponse like(String id) throws IOException {
        Comment commentBean = service.getCommentDetail(id);
        int likeCount = Integer.parseInt(commentBean.getLikeCount()) + 1;
        commentBean.setLikeCount(String.valueOf(likeCount));
        service.updateComment(commentBean);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }

    // 用户所有商品收到的评论
    @RequestMapping(value = "/listUserAllCommentsByUserAllThing", method = RequestMethod.GET)
    public APIResponse listUserAllCommentsByUserAllThing(String userId){
        List<Comment> list =  service.getUserAllCommentsByUserAllThingList(userId);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }


}
