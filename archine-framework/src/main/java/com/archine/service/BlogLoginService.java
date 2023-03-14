package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.User;

public interface BlogLoginService{
    
    ResponseResult login(User user);

    ResponseResult logout();
}
