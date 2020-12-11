package com.blog.controller.blog;

import com.blog.controller.common.Result;
import com.blog.controller.common.ResultGenerator;
import com.blog.entity.AdminUser;
import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.service.AdminUserService;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.service.TagService;
import com.blog.vo.BlogDetailVO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class MyBlogController {

    //private String theme = "amaze";

    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private AdminUserService adminUserService;
    @Resource
    private CommentService commentService;

    @GetMapping({"/", "/index", "/index.html"})
    public String index(Model model){
        return this.page(1, model);
    }

    /**
     * 公共代码抽取
     */
    private void addModel(Model model){
        //获取热门标签
        model.addAttribute("hotTags", tagService.findTagCountForIndex());
        //获取最新发布的博客标题
        model.addAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        //获取点击点击最多的博客标题
        model.addAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        //查询当前1号管理员信息
        model.addAttribute("adminUser", adminUserService.getUserById(1));
    }

    /**
     * 首页 数据展示
     * @return
     */
    @GetMapping("page/{pageNum}")
    public String page(@PathVariable Integer pageNum, Model model){
        //获取博客文章列表
        PageInfo<Blog> blogPageResult = blogService.findPage(pageNum, 5);
        model.addAttribute("blogPageResult", blogPageResult);

        this.addModel(model);

        model.addAttribute("pageName", "首页");
        return "blog/index";
    }

    /**
     * 首页 搜索功能
     */
    @GetMapping("/search/{keyword}/{pageNum}")
    public String search(@PathVariable String keyword, @PathVariable Integer pageNum, Model model){
        PageInfo<Blog> blogPageResult = blogService.findPageForIndex(keyword, pageNum, 5);
        model.addAttribute("blogPageResult", blogPageResult);
        this.addModel(model);
        model.addAttribute("pageUrl", "search");
        model.addAttribute("pageName", "搜索");
        return "blog/list";
    }

    /**
     * 首页 点击热门标签 列表页
     */
    @GetMapping("/tag/{keyword}/{pageNum}")
    public String tagSearch(@PathVariable String keyword, @PathVariable Integer pageNum, Model model) {
        PageInfo<Blog> blogPageResult = blogService.findPageForIndex(keyword, pageNum, 5);
        model.addAttribute("blogPageResult", blogPageResult);
        this.addModel(model);
        model.addAttribute("pageUrl", "tag");
        model.addAttribute("pageName", "热门标签");
        return "blog/list";
    }

    /**
     * 首页 点击分类 列表页
     */
    @GetMapping("/category/{keyword}/{pageNum}")
    public String categorySearch(@PathVariable String keyword, @PathVariable Integer pageNum, Model model) {
        PageInfo<Blog> blogPageResult = blogService.findPageForIndex(keyword, pageNum, 5);
        model.addAttribute("blogPageResult", blogPageResult);
        this.addModel(model);
        model.addAttribute("pageUrl", "category");
        model.addAttribute("pageName", "分类");
        return "blog/list";
    }

    /**
     * 首页 博客详情
     */
    @GetMapping("/blog/{blogId}")
    public String detail(@PathVariable Long blogId, @RequestParam(name = "commentPage", defaultValue = "1") Integer pageNum, Model model){
        BlogDetailVO blog = blogService.findByIdForIndex(blogId);
        if (blog != null){
            model.addAttribute("blog", blog);
        }
        PageInfo<Comment> commentPageResult = commentService.findPageForIndex(pageNum, 8, blogId);
        AdminUser adminUser = adminUserService.getUserById(1);
        model.addAttribute("pageName", "详情");
        model.addAttribute("commentPageResult", commentPageResult);
        model.addAttribute("adminUser", adminUser);
        return "blog/detail";
    }

    @GetMapping("/about")
    public String about(@RequestParam(name = "commentPage", defaultValue = "1") Integer pageNum, Model model){
        BlogDetailVO blog = blogService.findByIdForIndex(1L);
        if (blog != null){
            model.addAttribute("blog", blog);
        }
        PageInfo<Comment> commentPageResult = commentService.findPageForIndex(pageNum, 8, 1L);
        model.addAttribute("commentPageResult", commentPageResult);
        model.addAttribute("pageName", "about");
        return "blog/detail";
    }

    /**
     * 前台 评论提交
     */
    @PostMapping("/blog/comment")
    @ResponseBody
    public Result commentSubmit(@RequestParam Long blogId,
                                @RequestParam String commentator,
                                @RequestParam String email,
                                @RequestParam String commentBody,
                                HttpSession session,
                                Model model){
        //获取session域中的验证码
        /*String kaptchaCode = session.getAttribute("verifyCode") + "";
        if(kaptchaCode == null){
            return ResultGenerator.genFailResult("非法请求");
        }
        if (!kaptchaCode.equals(verifyCode)){
            return ResultGenerator.genFailResult("验证码错误");
        }*/
        if (null == blogId || blogId < 0) {
            return ResultGenerator.genFailResult("非法请求");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGenerator.genFailResult("评论内容过长");
        }
        Blog blog = blogService.findById(blogId);
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setBlogTitle(blog.getBlogTitle());
        comment.setCommentator(commentator);
        comment.setEmail(email);
        comment.setCommentBody(commentBody);
        if (commentService.saveComment(comment)) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("评论失败");
    }

}