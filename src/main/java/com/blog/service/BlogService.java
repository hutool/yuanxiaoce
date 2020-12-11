package com.blog.service;

import com.blog.entity.Blog;
import com.blog.controller.common.QueryBlog;
import com.blog.vo.BlogDetailVO;
import com.blog.vo.SimpleBlogListVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BlogService {

    /**
     * 保存博客
     */
    String saveBlog(Blog blog);

    Blog findById(Long blogId);

    /**
     * 首页 博客详情
     */
    BlogDetailVO findByIdForIndex(Long blogId);

    /**
     * 管理系统 搜索实现
     */
    PageInfo<Blog> findPage(QueryBlog queryBlog);

    PageInfo<Blog> findPage(Integer pageNum, Integer pageSize);

    /**
     * 首页 搜索实现
     */
    PageInfo<Blog> findPageForIndex(String keyword, Integer pageNum, Integer pageSize);

    String updateBlog(Blog blog);

    /**
     * 批量删除
     */
    Boolean batchDelete(Integer[] ids);

    /**
     * 【首页】侧边栏数据列表
     * 0-点击最多，1-最新发布
     */
    List<SimpleBlogListVO> getBlogListForIndexPage(Integer type);

    Boolean updateCommentCount(Long blogId);

    Boolean deleteCommentCount(Long blogId);
}
