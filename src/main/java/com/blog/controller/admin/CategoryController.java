package com.blog.controller.admin;

import com.blog.controller.common.Result;
import com.blog.controller.common.ResultGenerator;
import com.blog.entity.Category;
import com.blog.service.CategoryService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//分类列表
@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String category(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                           Model model){
        model.addAttribute("path", "categories");
        PageInfo page = categoryService.findPage(pageNum, pageSize);
        model.addAttribute("page", page);
        return "admin/category";
    }

    //新增
    @PostMapping("/categories/save")
    @ResponseBody
    public Result save(String categoryName){
        if (StringUtils.isEmpty(categoryName)){
            return ResultGenerator.genFailResult("请输入分类名称");
        }
        if (categoryService.insert(categoryName)){
            //保存成功，向前端返回数据
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("分类名称重复");
    }

    @GetMapping("/categories/info/{id}")
    @ResponseBody
    public Result info(@PathVariable Integer id){
        if(id != null){
            Category category = categoryService.selectById(id);
            return ResultGenerator.genSuccessResult(category);
        }
        return ResultGenerator.genFailResult("数据不存在");
    }

    //修改
    @PostMapping("/categories/update/{categoryId}")
    @ResponseBody
    public Result update(@PathVariable int categoryId, String categoryName, String categoryIcon){
        if (categoryService.updateById(categoryId, categoryName)) {
           return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("修改失败");
    }

    //分类删除
    @RequestMapping("/categories/delete/{categoryId}")
    @ResponseBody
    public Result delete(@PathVariable int categoryId){
        if (categoryService.deleteById(categoryId)) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("删除失败，找不到该分类！");
    }
}