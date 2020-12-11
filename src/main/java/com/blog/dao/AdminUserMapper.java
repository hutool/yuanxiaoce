package com.blog.dao;

import com.blog.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

public interface AdminUserMapper {

    /**
     * 根据Id查询
     */
    AdminUser selectByPrimaryKey(Integer AdminUserId);

    /**
     * 新增
     */
    int insert(AdminUser adminUser);

    /**
     * 修改用户信息
     */
    int updateByPrimaryKey(AdminUser adminUser);

    /**
     * 登录
     */
    AdminUser login(String userName, String password);
}
