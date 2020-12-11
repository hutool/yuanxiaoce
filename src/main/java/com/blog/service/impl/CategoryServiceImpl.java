package com.blog.service.impl;

import com.blog.dao.CategoryMapper;
import com.blog.entity.Category;
import com.blog.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.expression.Strings;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Category selectById(Integer categoryId) {
        return categoryMapper.selectById(categoryId);
    }

    @Override
    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    @Override
    @Transactional
    public Boolean insert(String categoryName) {
        Category c = categoryMapper.selectByName(categoryName);
        //如果数据库中不存在分类名称，则执行保存
        if (c == null){
            Category category = new Category();
            category.setCategoryName(categoryName);
            return categoryMapper.insert(category) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateById(int categoryId, String categoryName) {
        Category c = categoryMapper.selectById(categoryId);
        if (c != null) {
            Category category = new Category();
            category.setCategoryId(categoryId);
            category.setCategoryName(categoryName);

            return categoryMapper.updateById(category) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteById(int categoryId) {
        Category c = categoryMapper.selectById(categoryId);
        if( c != null) {
            return categoryMapper.deleteById(categoryId) > 0;
        }
        return false;
    }

    @Override
    public PageInfo findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> categories = categoryMapper.selectAll();
        return new PageInfo<Category>(categories);
    }
}
