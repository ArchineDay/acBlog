package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.RoleDto;
import com.archine.domain.vo.PageVo;
import com.archine.domain.vo.RoleVo;
import com.archine.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<PageVo> getList(Integer pageNum, Integer pageSize, RoleDto roleDto){
        return roleService.getList(pageNum,pageSize,roleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleDto roleDto){

        return roleService.changeStatus(roleDto);
    }

    @PostMapping
    public  ResponseResult addRole(@RequestBody RoleDto roleDto){

        return roleService.addRole(roleDto);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleDto roleDto){
        return roleService.updateRole(roleDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteRoleById(@PathVariable List<Long> id){
        return roleService.deleteRoleById(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();

    }


}
