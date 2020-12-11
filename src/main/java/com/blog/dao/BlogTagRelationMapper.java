package com.blog.dao;

import com.blog.entity.BlogTagRelation;

import java.util.List;

public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Long relationId);

    int deleteByBlogId(Long blogId);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    int batchInsert(List relationList);

    BlogTagRelation selectByPrimaryKey(Long relationId);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);
}