package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.RoleDto;
import com.archine.domain.entity.Role;
import com.archine.domain.vo.RoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-30 19:16:12
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getList(Integer pageNum, Integer pageSize, RoleDto roleDto);

    ResponseResult changeStatus( RoleDto roleDto);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult updateRole(RoleDto roleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult deleteRoleById(List<Long> id);

    ResponseResult listAllRole();
}


