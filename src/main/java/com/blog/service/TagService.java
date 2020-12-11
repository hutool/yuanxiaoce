package com.blog.service;

import com.blog.entity.Tag;
import com.blog.entity.TagCount;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TagService {

    List<Tag> selectAll();

    PageInfo findPage(int pageNum, int pageSize);

    Boolean insert(String tagName);

    Boolean deleteById(int tagId);

    List<TagCount> findTagCountForIndex();
}
