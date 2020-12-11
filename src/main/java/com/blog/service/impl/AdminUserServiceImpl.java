package com.blog.service.impl;

import com.blog.dao.AdminUserMapper;
import com.blog.entity.AdminUser;
import com.blog.service.AdminUserService;
import com.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.annotation.Resources;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper userMapper;

    @Override
    public AdminUser login(String userName, String password) {
        return userMapper.login(userName, MD5Util.MD5Encode(password, "UTF-8"));
    }

    @Override
    public AdminUser getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改当前用户的信息
     */
    @Override
    @Transactional
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = userMapper.selectByPrimaryKey(loginUserId);
        if (adminUser != null) {
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            return userMapper.updateByPrimaryKey(adminUser) > 0;
        }
        return false;
    }

    /**
     * 修改密码
     */
    @Override
    @Transactional
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = userMapper.selectByPrimaryKey(loginUserId);
        if (adminUser != null){
            //比较原密码是否正确
            if (MD5Util.MD5Encode(originalPassword, "UTF-8").equals(adminUser.getLoginPassword())) {
                //设置新密码
                adminUser.setLoginPassword(MD5Util.MD5Encode(newPassword, "UTF-8"));
                return userMapper.updateByPrimaryKey(adminUser) > 0;
            }
        }
        return false;
    }
}
