package com.archine.service.impl;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.RoleDto;
import com.archine.domain.entity.Role;
import com.archine.domain.vo.PageVo;
import com.archine.mapper.RoleMapper;
import com.archine.service.RoleService;
import com.archine.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-30 19:16:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

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

        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVos = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVos);
    }


    @Override
    public ResponseResult changeStatus(RoleDto roleDto) {

        //从vo中获取角色id和状态
        Long roleId = roleDto.getRoleId();
        String status = roleDto.getStatus();
        //获取角色
        Role role = getById(roleId);
        //修改角色状态
        role.setStatus(status);
        updateById(role);

        return ResponseResult.okResult();
    }
}


