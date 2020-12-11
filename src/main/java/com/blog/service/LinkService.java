package com.blog.service;

import com.blog.entity.Link;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface LinkService {

    Boolean save(Link link);

    Link findById(int id);

    List<Link> findAll();

    PageInfo<Link> findPage(int pageNum, int pageSize);

    Boolean update(Link link);

    Boolean deleteById(Integer id);

    Boolean batchDelete(Integer[] ids);
}
