package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.UserDto;
import com.archine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

   @GetMapping("/list")
    public ResponseResult getList(Integer pageNum, Integer pageSize, UserDto userDto){
        return userService.getList(pageNum,pageSize,userDto);
    }
}
