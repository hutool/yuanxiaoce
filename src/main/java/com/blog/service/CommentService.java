package com.blog.service;

import com.blog.entity.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {

    /**
     * 根据ids批量审核
     * 将数据库字段comment_status设为1，即可通过审核
     */
    Boolean checkDone(Long[] ids);

    PageInfo<Comment> findPage(Integer pageNum, Integer pageSize);

    PageInfo<Comment> findPageForIndex(Integer pageNum, Integer pageSize, Long blogId);

    /**
     * 根据ids，批量删除
     */
    Boolean batchDelete(Long[] ids);

    Comment findById(Long id);

    Boolean reply(Long commentId, String replyBody);

    /**
     * 保存评论
     */
    Boolean saveComment(Comment comment);

}
