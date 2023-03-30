package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.entity.Menu;
import com.archine.mapper.MenuMapper;
import com.archine.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-30 18:50:46
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
//    @Autowired
//    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，直接返回所有权限
        if (id == 1L) {
            LambdaQueryWrapper<Menu> queryWrapper =new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
      // return  menuMapper.selectPermsByUserId(id);
        return  getBaseMapper().selectPermsByUserId(id);
    }
}


