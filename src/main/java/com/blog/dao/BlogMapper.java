package com.blog.dao;

import com.blog.entity.Blog;

import java.util.List;

public interface BlogMapper {
    int deleteByPrimaryKey(Long blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long blogId);

    List<Blog> selectAll();

    //后台管理系统 搜索实现
    List<Blog> search(String keyword);

    //首页 搜索实现
    List<Blog> searchForIndex(String keyword);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);

    //批量删除
    int batchDelete(Integer[] ids);

    /*
     * 根据类型查询blog
     * type：0-点击最多，1-最新发布
     * limit：展示数量
     */
    List<Blog> findBlogByType(Integer type, Integer limit);

    List<Blog> findBlogForIndex();

    Blog findByIdForIndex(Long blogId);

    int updateCommentCount(Long blogId);

    int deleteCommentCount(Long blogId);
}