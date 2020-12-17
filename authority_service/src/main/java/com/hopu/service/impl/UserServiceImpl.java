package com.hopu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hopu.domain.Role;
import com.hopu.domain.User;
import com.hopu.domain.UserRole;
import com.hopu.mapper.UserMapper;
import com.hopu.service.IUserRoleService;
import com.hopu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public void setRole(String userId, List<Role> roles) {
        // 先移除当前用户之前关联的角色
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",userId));
        // 再对当用户绑定新的角色
        roles.forEach(role -> {
            UserRole userRole =new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUserId(userId);
            userRoleService.save(userRole);
        });
    }
}
