package com.blog.dao;

import com.blog.entity.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    List<Comment> selectAll();

    List<Comment> selectAllForIndex(Long blogId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int checkDone(Long[] ids);

    int batchDelete(Long[] ids);

}