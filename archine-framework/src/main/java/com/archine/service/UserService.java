package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Link;
import com.archine.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-14 19:11:46
 */
@Repository
public interface UserService extends IService<User> {
    ResponseResult userInfo();
}


