package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.User;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.exception.SystemException;
import com.archine.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController
public  class LoginController {
    @Autowired
    private LoginService LoginService;

    @PostMapping("/user/login")
    public ResponseResult AdminLogin(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
            //提示必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return LoginService.login(user);
    }

}
