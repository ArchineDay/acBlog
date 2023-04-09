package com.archine.service.impl;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.RoleDto;
import com.archine.domain.dto.RoleStatusDto;
import com.archine.domain.entity.Role;
import com.archine.domain.entity.RoleMenu;
import com.archine.domain.vo.PageVo;
import com.archine.domain.vo.RoleVo;
import com.archine.mapper.RoleMapper;
import com.archine.service.MenuService;
import com.archine.service.RoleMenuService;
import com.archine.service.RoleService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-30 19:16:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleService roleService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是管理员，返回集合中只需要admin
        if (SecurityUtils.isAdmin()) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult<PageVo> getList(Integer pageNum, Integer pageSize, RoleDto roleDto) {
        //角色列表分页查询
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleDto.getRoleName()),Role::getRoleName, roleDto.getRoleName());
        queryWrapper.like(StringUtils.hasText(roleDto.getStatus()),Role::getStatus, roleDto.getStatus());
        //按照升序排序
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVos = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVos);
    }


    @Override
    public ResponseResult changeStatus(RoleStatusDto roleDto) {

        //从dto中获取角色id和状态
        Long roleId = roleDto.getRoleId();
        String status = roleDto.getStatus();
        //获取角色
        Role role = getById(roleId);
        //修改角色状态
        role.setStatus(status);
        updateById(role);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);
        //添加角色菜单关联
        return addRoleMenu(roleDto, role);
    }

    @Override
    public ResponseResult updateRole(RoleDto roleDto) {
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        //修改角色信息
        updateById(role);
        //删除角色菜单关联
        roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, role.getId()));
        //添加角色菜单关联
        return addRoleMenu(roleDto, role);
    }

    @NotNull
    private ResponseResult addRoleMenu(RoleDto roleDto, Role role) {
        List<RoleMenu> roleMenus = roleDto.getMenuIds().stream()
                .map(menuId -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(role.getId());
                    roleMenu.setMenuId(menuId);
                    return roleMenu;
                })
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult deleteRoleById(List<Long> id) {
        removeByIds(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        List<Role> roles = roleService.list();
        return ResponseResult.okResult(roles);
    }

}


