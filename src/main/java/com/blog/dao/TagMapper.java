package com.blog.dao;

import com.blog.entity.Tag;
import com.blog.entity.TagCount;

import java.util.List;

public interface TagMapper {

    Tag selectById(Integer tagId);

    Tag selectByName(String tagName);

    List<Tag> selectAll();

    int insert(Tag tag);

    int updateById(Tag tag);

    int batchInsertTag(List<Tag> tagList);

    int deleteById(Integer tagId);

    List<TagCount> getTagCount();
}
