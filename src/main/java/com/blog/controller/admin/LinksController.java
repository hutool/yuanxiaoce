package com.blog.controller.admin;

import com.blog.controller.common.Result;
import com.blog.controller.common.ResultGenerator;
import com.blog.entity.Link;
import com.blog.service.LinkService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class LinksController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/links")
    public String links(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                        Model model){
        PageInfo<Link> page = linkService.findPage(pageNum, pageSize);
        model.addAttribute("path", "links");
        model.addAttribute("page", page);
        return "admin/link";
    }

    @PostMapping("/links/save")
    @ResponseBody
    public Result save(Link link){
        if(linkService.save(link)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("保存失败");
    }

    @PostMapping("/links/update")
    @ResponseBody
    public Result update(Link link){
        if (linkService.update(link)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("数据不存在");
    }

    @RequestMapping("/links/info/{id}")
    @ResponseBody
    public Result info(@PathVariable Integer id){
        if(id != null){
            Link link = linkService.findById(id);
            return ResultGenerator.genSuccessResult(link);
        }
        return ResultGenerator.genFailResult("数据不存在");
    }

    //批量删除
    @PostMapping("links/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids){
        if (linkService.batchDelete(ids)){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("删除失败");
    }
}