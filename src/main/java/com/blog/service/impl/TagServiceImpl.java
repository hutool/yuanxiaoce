package com.blog.service.impl;

import com.blog.dao.TagMapper;
import com.blog.entity.Tag;
import com.blog.entity.TagCount;
import com.blog.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> selectAll() {
        return tagMapper.selectAll();
    }

    @Override
    public PageInfo findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagMapper.selectAll();
        return new PageInfo<>(tags);
    }

    @Override
    @Transactional
    public Boolean insert(String tagName) {
        Tag tag = tagMapper.selectByName(tagName);
        if (tag == null) {
            Tag t = new Tag();
            t.setTagName(tagName);
            return tagMapper.insert(t) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteById(int tagId) {
        Tag tag = tagMapper.selectById(tagId);
        if (tag != null) {
            return tagMapper.deleteById(tagId) > 0;
        }
        return false;
    }

    @Override
    public List<TagCount> findTagCountForIndex() {
        return tagMapper.getTagCount();
    }

}
