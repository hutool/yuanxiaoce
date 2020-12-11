package com.blog.service.impl;

import com.blog.dao.BlogMapper;
import com.blog.dao.BlogTagRelationMapper;
import com.blog.dao.CategoryMapper;
import com.blog.dao.TagMapper;
import com.blog.entity.*;
import com.blog.service.BlogService;
import com.blog.controller.common.QueryBlog;
import com.blog.util.MarkDownUtil;
import com.blog.vo.BlogDetailVO;
import com.blog.vo.SimpleBlogListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private BlogTagRelationMapper blogTagRelationMapper;

    @Override
    @Transactional
    public String saveBlog(Blog blog) {
        Category blogCategory = categoryMapper.selectById(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        //保存文章
        if (blogMapper.insertSelective(blog) > 0) {
            System.out.println("========="+blog);
            //新增的tag对象
            List<Tag> tagListForInsert = new ArrayList<>();
            //所有的tag对象，用于建立关系数据
            List<Tag> allTagsList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                Tag tag = tagMapper.selectByName(tags[i]);
                if (tag == null) {
                    //不存在就新增
                    Tag tempTag = new Tag();
                    tempTag.setTagName(tags[i]);
                    tagListForInsert.add(tempTag);
                } else {
                    allTagsList.add(tag);
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                tagMapper.batchInsertTag(tagListForInsert);
            }
            categoryMapper.updateById(blogCategory);
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            //新增关系数据
            allTagsList.addAll(tagListForInsert);
            for (Tag tag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                return "success";
            }
        }
        return "保存失败";
    }

    @Override
    public Blog findById(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public BlogDetailVO findByIdForIndex(Long blogId) {
        Blog blog = blogMapper.findByIdForIndex(blogId);
        if(blog != null){
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            //更新
            blogMapper.updateByPrimaryKeySelective(blog);

            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            //md格式转换
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            if (!StringUtils.isEmpty(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            return blogDetailVO;
        }
        return null;
    }

    // 分页
    @Override
    public PageInfo<Blog> findPage(QueryBlog queryBlog) {
        // 如果没有keyword搜索关键字，说明不是搜索，直接分页查询所有
        if(queryBlog.getKeyword() == null || "".equals(queryBlog.getKeyword())){
            PageHelper.startPage(queryBlog.getPageNum(), queryBlog.getPageSize());
            List<Blog> blogs = blogMapper.selectAll();
            return new PageInfo<>(blogs);
        }else {
            // 根据输入的关键字，模糊查询
            PageHelper.startPage(queryBlog.getPageNum(), queryBlog.getPageSize());
            List<Blog> blogs = blogMapper.search(queryBlog.getKeyword());
            return new PageInfo<>(blogs);
        }
    }

    //首页 blog列表
    @Override
    public PageInfo<Blog> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.findBlogForIndex();
        return new PageInfo<>(blogs);
    }

    //首页 搜索实现
    @Override
    public PageInfo<Blog> findPageForIndex(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.searchForIndex(keyword);
        return new PageInfo<>(blogs);
    }

    @Override
    @Transactional
    public String updateBlog(Blog blog) {
        Blog b = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if(b == null) {
            return "数据不存在";
        }
        b.setBlogTitle(blog.getBlogTitle());
        b.setBlogSubUrl(blog.getBlogSubUrl());
        b.setBlogContent(blog.getBlogContent());
        b.setBlogCoverImage(blog.getBlogCoverImage());
        b.setBlogStatus(blog.getBlogStatus());
        b.setEnableComment(blog.getEnableComment());

        Category category = categoryMapper.selectById(blog.getBlogCategoryId());
        if (category == null) {
            b.setBlogCategoryId(0);
            b.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            b.setBlogCategoryName(category.getCategoryName());
            b.setBlogCategoryId(category.getCategoryId());
            //分类的排序值加1
            category.setCategoryRank(category.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        b.setBlogTags(blog.getBlogTags());
        //新增的tag对象
        List<Tag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<Tag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            Tag tag = tagMapper.selectByName(tags[i]);
            if (tag == null) {
                //不存在就新增
                Tag tempTag = new Tag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagMapper.batchInsertTag(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for (Tag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
        categoryMapper.updateById(category);
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(b) > 0) {
            return "success";
        }
        return "修改失败";
    }

    @Override
    @Transactional
    public Boolean batchDelete(Integer[] ids) {
        if (ids.length < 1){
            return false;
        }
        return blogMapper.batchDelete(ids) > 0;
    }

    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(Integer type) {
        List<Blog> blogs = blogMapper.findBlogByType(type, 9);
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
        if(blogs != null){
            //将数据库中查询出来的blogs封装到VO中
            for(Blog blog : blogs){
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                simpleBlogListVO.setBlogId(blog.getBlogId());
                simpleBlogListVO.setBlogTitle(blog.getBlogTitle());
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }

    @Override
    public Boolean updateCommentCount(Long blogId) {
        return blogMapper.updateCommentCount(blogId) > 0;
    }

    @Override
    public Boolean deleteCommentCount(Long blogId) {
        return blogMapper.deleteCommentCount(blogId) > 0;
    }
}
