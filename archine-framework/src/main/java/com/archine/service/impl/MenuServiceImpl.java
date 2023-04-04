package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Menu;
import com.archine.domain.vo.MenuVo;
import com.archine.mapper.MenuMapper;
import com.archine.service.MenuService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        if (SecurityUtils.isAdmin()) {
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

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<MenuVo> menus =null;
        //如果是管理员，直接返回所有菜单
        if (SecurityUtils.isAdmin()){
           menus =  menuMapper.selectAllRouterMenu();

        }else {
            //否则返回当前用户所具有的菜单
           menus = menuMapper.selectRouterMenuByUserId(userId);
        }
        //构建menuTree
        List<MenuVo> menuTree = buildMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult getList(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //针对菜单名进行模糊查询
        queryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName);
        //针对菜单状态进行查询
        queryWrapper.like(StringUtils.hasText(status),Menu::getStatus,status);

        //菜单要按照父菜单id和orderNum进行排序
        LambdaQueryWrapper<Menu> wrapper = queryWrapper.orderByAsc(Menu::getParentId, Menu::getOrderNum);
        //封装成menuVo
        List<Menu> menuList = list(wrapper);
        List<MenuVo> menuVoList = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        return ResponseResult.okResult(menuVoList);
    }


    private List<MenuVo> buildMenuTree(List<MenuVo> menus, long parentId) {
        List<MenuVo> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数的子菜单
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {

        List<MenuVo> childrenList = menus.stream()
                //menus中父id等于menu的id，即menu的子menu
                .filter(m -> m.getParentId().equals(menu.getId()))
                //递归获取子菜单，map作用是将stream中的每个元素都映射到另一个stream中
                .map(m -> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
}


