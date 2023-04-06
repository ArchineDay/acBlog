package com.archine.service.impl;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.UserDto;
import com.archine.domain.entity.User;
import com.archine.domain.vo.PageVo;
import com.archine.domain.vo.UserInfoVo;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.exception.SystemException;
import com.archine.mapper.UserMapper;
import com.archine.service.UserService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.ws.Response;
import java.util.List;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-14 19:09:50
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULLT);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }

        //判断数据是否存在
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize, UserDto userDto) {
        //查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userDto.getUserName()),User::getUserName,userDto.getUserName());
        queryWrapper.eq(StringUtils.hasText(userDto.getPhoneNumber()), User::getPhoneNumber, userDto.getPhoneNumber());
        queryWrapper.eq(StringUtils.hasText(userDto.getStatus()), User::getStatus, userDto.getStatus());
        //分页查询
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        count(queryWrapper);
        return count(queryWrapper)>0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        count(queryWrapper);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        count(queryWrapper);
        //大于0表示存在
        return count(queryWrapper)>0;
    }
}


