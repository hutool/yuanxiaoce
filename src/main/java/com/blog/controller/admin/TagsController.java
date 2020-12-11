package com.blog.controller.admin;

import com.blog.controller.common.Result;
import com.blog.controller.common.ResultGenerator;
import com.blog.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class TagsController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@RequestParam(name="pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(name="pageSize", defaultValue = "10") int pageSize, Model model){
        PageInfo page = tagService.findPage(pageNum, pageSize);
        model.addAttribute("path", "tags");
        model.addAttribute("page", page);
        return "/admin/tag";
    }

    @PostMapping("/tags/save")
    @ResponseBody
    public Result save(@RequestParam String tagName){
        if (tagService.insert(tagName)) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("保存失败");
    }

    @GetMapping("/tags/delete/{tagId}")
    @ResponseBody
    public Result delete(@PathVariable int tagId){
        if (tagService.deleteById(tagId)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("删除失败！");
    }
}
