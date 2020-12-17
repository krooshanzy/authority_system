package com.hopu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hopu.domain.Menu;
import com.hopu.domain.Role;
import com.hopu.domain.RoleMenu;
import com.hopu.mapper.RoleMapper;
import com.hopu.service.IRoleMenuService;
import com.hopu.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public void setMenu(String roleId, List<Menu> menus) {
        // 先清除与当前角色管理的所有权限，然后再重新赋值权限
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleId));

        menus.forEach(menu -> {
            RoleMenu roleMenu =new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menu.getId());
            roleMenuService.save(roleMenu);
        });
    }
}
