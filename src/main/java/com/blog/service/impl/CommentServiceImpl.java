package com.blog.service.impl;

import com.blog.dao.CommentMapper;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public Boolean checkDone(Long[] ids) {
        return commentMapper.checkDone(ids) > 0;
    }

    @Override
    public PageInfo<Comment> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectAll();
        return new PageInfo<>(comments);
    }

    @Override
    public PageInfo<Comment> findPageForIndex(Integer pageNum, Integer pageSize, Long blogId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectAllForIndex(blogId);
        return new PageInfo<>(comments);
    }

    @Override
    public Boolean batchDelete(Long[] ids) {
        return commentMapper.batchDelete(ids) > 0;
    }

    @Override
    public Comment findById(Long id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if(comment != null && comment.getCommentStatus() == 1){
            comment.setReplyBody(replyBody);
            comment.setReplyCreateTime(new Date());
            return commentMapper.updateByPrimaryKeySelective(comment) > 0;
        }
        return false;
    }

    @Override
    public Boolean saveComment(Comment comment) {
        return commentMapper.insertSelective(comment) > 0;
    }
}
