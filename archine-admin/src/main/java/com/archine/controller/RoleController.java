package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.RoleDto;
import com.archine.domain.vo.PageVo;
import com.archine.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
