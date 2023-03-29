package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);
}
