package com.blog.service;

import com.blog.entity.AdminUser;

public interface AdminUserService {

    //登录
    AdminUser login(String userName, String password);

    AdminUser getUserById(Integer id);

    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);
}
