package com.blog.service.impl;

import com.blog.dao.LinkMapper;
import com.blog.entity.Link;
import com.blog.service.LinkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    @Transactional
    public Boolean save(Link link) {
        return linkMapper.insertSelective(link) > 0;
    }

    @Override
    public Link findById(int id) {
        return linkMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Link> findAll() {
        return linkMapper.selectAll();
    }

    @Override
    public PageInfo<Link> findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Link> links = linkMapper.selectAll();
        return new PageInfo<>(links);
    }

    @Override
    @Transactional
    public Boolean update(Link link) {
        Link l = linkMapper.selectByPrimaryKey(link.getLinkId());
        if (l != null){
            return linkMapper.updateByPrimaryKeySelective(link) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteById(Integer id) {
        Link l = linkMapper.selectByPrimaryKey(id);
        if (l != null){
            return linkMapper.deleteByPrimaryKey(id) > 0;
        }
        return false;
    }

    @Override
    public Boolean batchDelete(Integer[] ids) {
        if(ids.length < 1){
            return false;
        }
        return linkMapper.batchDelete(ids) > 0;
    }
}
