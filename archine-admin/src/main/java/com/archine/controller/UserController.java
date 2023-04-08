package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.RoleDto;
import com.archine.domain.dto.UpdateUserDto;
import com.archine.domain.dto.UserDto;
import com.archine.domain.dto.UserStatusDto;
import com.archine.domain.entity.User;
import com.archine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

   @GetMapping("/list")
    public ResponseResult getList(Integer pageNum, Integer pageSize, UserDto userDto){
        return userService.getList(pageNum,pageSize,userDto);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    //PathVariable 用于获取url中的数据
    public ResponseResult deleteUser(@PathVariable List<Long> id){
        return userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UpdateUserDto userDto){
        return userService.updateUser(userDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody UserStatusDto userDto){
        return userService.changeStatus(userDto);
    }

}
