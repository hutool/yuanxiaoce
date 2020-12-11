package com.blog.dao;

import com.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//分类管理
public interface CategoryMapper {

    Category selectById(Integer categoryId);

    Category selectByName(String categoryName);

    List<Category> selectAll();

    int insert(Category category);

    int updateById(Category category);

    int deleteById(Integer categoryId);
}
