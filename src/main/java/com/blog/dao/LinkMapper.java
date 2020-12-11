package com.blog.dao;

import com.blog.entity.Link;

import java.util.List;

public interface LinkMapper {

    int deleteByPrimaryKey(Integer linkId);

    int batchDelete(Integer[] ids);

    int insert(Link record);

    int insertSelective(Link record);

    Link selectByPrimaryKey(Integer linkId);

    List<Link> selectAll();

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);
}