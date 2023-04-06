package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Menu;
import com.archine.domain.vo.MenuVo;
import com.archine.domain.vo.TreeSelectVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-30 18:50:42
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getList(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult updateMenu(Menu menu) throws RuntimeException;

    ResponseResult getMenu(Long id);

    ResponseResult deleteMenu(Long id);

    List<TreeSelectVo> treeSelect();

    ResponseResult roleMenuTreeSelect(Long id);
}


