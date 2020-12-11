package com.blog.controller.admin;

import com.blog.controller.common.Result;
import com.blog.controller.common.ResultGenerator;
import com.blog.entity.Comment;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class CommentController {

    @Resource
    private CommentService commentService;
    @Resource
    private BlogService blogService;

    @GetMapping("/comments")
    public String comment(Model model,
                          @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<Comment> page = commentService.findPage(pageNum, pageSize);
        model.addAttribute("path", "comments");
        model.addAttribute("page", page);
        return "admin/comment";
    }

    /**
     * 评论审核
     * 可根据id批量审核
     */
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result checkDone(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (commentService.checkDone(ids)) {
            for (Long id : ids) {
                Comment comment = commentService.findById(id);
                blogService.updateCommentCount(comment.getBlogId());
            }
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("审核失败");
        }
    }

    /**
     * 评论删除
     * 可根据id批量删除
     */
    @PostMapping("/comments/delete")
    @ResponseBody
    public Result delete(@RequestBody Long[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        for (Long id : ids) {
            Comment comment = commentService.findById(id);
            if (commentService.batchDelete(ids)) {
                blogService.deleteCommentCount(comment.getBlogId());
            }
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("删除失败");
    }

    @GetMapping("/comments/info/{ids}")
    @ResponseBody
    public Result info(@PathVariable Long ids) {
        if (ids != null) {
            Comment comment = commentService.findById(ids);
            return ResultGenerator.genSuccessResult(comment);
        } else {
            return ResultGenerator.genFailResult("数据不存在");
        }
    }

    /**
     * 评论回复
     * 指定评论者ID进行回复
     */
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result reply(Long commentId, String replyBody) {
        if (commentId == null || replyBody == null) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (commentService.reply(commentId, replyBody)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("回复失败");
        }
    }
}
