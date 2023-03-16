package com.archine.service.impl;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.User;
import com.archine.domain.vo.UserInfoVo;
import com.archine.mapper.UserMapper;
import com.archine.service.UserService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.SecurityUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;

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
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        User user = getById(userId);
        //封装成vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}


