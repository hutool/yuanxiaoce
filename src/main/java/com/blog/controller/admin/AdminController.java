package com.blog.controller.admin;

import com.blog.entity.AdminUser;
import com.blog.service.AdminUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService userService;

    //登录页面
    @GetMapping({"", "/login"})
    public String login() {
        return "admin/login";
    }

    //登录验证
    @PostMapping("/login")
    public String login(String userName,
                        String password,
                        String verifyCode,
                        HttpSession session) {
        //先对验证码，用户名和密码进行非空验证
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空！");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空！");
            return "admin/login";
        }
        //比对验证码，获取session域中的验证码
        String realVerifyCode = (String) session.getAttribute("verifyCode");
        if (StringUtils.isEmpty(verifyCode) || !realVerifyCode.equals(verifyCode)) {
            session.setAttribute("errorMsg", "验证码错误！");
            return "admin/login";
        }
        AdminUser adminUser = userService.login(userName, password);
        if (adminUser != null) {
            session.setAttribute("adminUser", adminUser);
            session.setAttribute("loginUser", adminUser.getNickName());
            session.setAttribute("loginUserId", adminUser.getAdminUserId());
            //session过期时间设置为7200秒 即两小时
            session.setMaxInactiveInterval(60 * 60 * 2);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMsg", "用户名或密码错误！");
            return "admin/login";
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("path", "index");
        return "admin/index";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer id = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = userService.getUserById(id);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        //return "admin/profile";
        return "admin/refuse";
    }

    //修改基本信息
    @PostMapping("/profile/name")
    @ResponseBody
    public String updateName(String loginUserName, String nickName, HttpServletRequest request) {
        if (Strings.isEmpty(loginUserName) || Strings.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer loginUserId = (Integer) request.getSession().getAttribute("loginUserId");
        if (userService.updateName(loginUserId, loginUserName, nickName)) {
            return "success";
        }
        return "修改失败";
    }

    //修改密码
    @PostMapping("/profile/password")
    @ResponseBody
    public String updatePassword(String originalPassword, String newPassword, HttpServletRequest request) {
        if (Strings.isEmpty(originalPassword) || Strings.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (Integer) request.getSession().getAttribute("loginUserId");
        if (userService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后，清除原session域中的数据
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return "success";
        }
        return "修改失败";
    }

    //退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        session.removeAttribute("loginUserId");
        session.removeAttribute("errorMsg");
        return "redirect:/admin/login";
    }
}
