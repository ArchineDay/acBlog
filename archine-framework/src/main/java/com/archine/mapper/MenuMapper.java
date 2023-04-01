package com.archine.mapper;

import com.archine.domain.entity.Menu;
import com.archine.domain.vo.MenuVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-30 18:50:51
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuVo> selectAllRouterMenu();

    List<MenuVo> selectRouterMenuByUserId(Long userId);

    List<String> selectPermsByUserId(Long id);
}


