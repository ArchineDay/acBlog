package com.archine.service.impl;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.User;
import com.archine.mapper.UserMapper;
import com.archine.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 19:09:50
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        return null;
    }
}


