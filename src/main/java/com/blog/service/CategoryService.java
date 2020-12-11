package com.blog.service;

import com.blog.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {

    Category selectById(Integer categoryId);

    List<Category> selectAll();

    Boolean insert(String categoryName);

    Boolean updateById(int categoryId, String categoryName);

    Boolean deleteById(int categoryId);

    PageInfo findPage(int pageNum, int pageSize);
}
