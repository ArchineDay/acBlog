package com.archine.service.impl;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.UpdateUserDto;
import com.archine.domain.dto.UserDto;
import com.archine.domain.entity.Role;
import com.archine.domain.entity.User;
import com.archine.domain.entity.UserRole;
import com.archine.domain.vo.*;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.exception.SystemException;
import com.archine.mapper.RoleMapper;
import com.archine.mapper.UserMapper;
import com.archine.service.RoleService;
import com.archine.service.UserRoleService;
import com.archine.service.UserService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;
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
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
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
        //转换成UserDetailVo对象
        List<UserDetailVo> userDetailVoList = page.getRecords().stream()
                .map(user -> {
                    //将User对象的属性逐一赋值给UserDetailVo对象的属性
                    UserDetailVo userDetailVo = BeanCopyUtils.copyBean(user, UserDetailVo.class);
                    return userDetailVo;
                })
                .collect(Collectors.toList());
        //封装数据返回
        PageVo pageVo = new PageVo(userDetailVoList, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUser(UserDto userDto) {
        //将userDto对象的属性逐一赋值给user对象的属性
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        //密码加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //用户名不能为空
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        //用户名必须之前未存在
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //手机号必须之前未存在
        if (StringUtils.hasText(user.getPhoneNumber()) && phoneExist(user.getPhoneNumber())){
            throw new SystemException(AppHttpCodeEnum.PHONE_EXIST);
        }
        //邮箱必须之前未存在
        if (StringUtils.hasText(user.getEmail()) && emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        save(user);
        //保存用户角色关系
        List<UserRole> userRoleList = userDto.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId))
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(List<Long> id) {
        //判断是否为当前用户
        Long userId = SecurityUtils.getUserId();
        if (id.contains(userId)){
            throw new SystemException(AppHttpCodeEnum.CANNOT_DELETE_SELF);
        }
        removeByIds(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getUser(Long id) {
        User user = getById(id);
        //根据用户id查询roleIds
        List<Long> roleIds =roleMapper.findRoleIdsByUserId(id);
        //查询所有角色
        List<Role> roles = roleService.list();

        getUserVo getUserVo = new getUserVo(roleIds, roles, user);
        return ResponseResult.okResult(getUserVo);
    }

    @Override
    public ResponseResult updateUser(UpdateUserDto userDto) {
        //将userDto对象的属性逐一赋值给user对象的属性
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        //删除原来的用户角色关联关系
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);
        //添加新的用户角色关联关系
        userRoleService.saveBatch(userDto.getRoleIds().stream()
                .map(roleId -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(roleId);
                    return userRole;
                })
                .collect(Collectors.toList()));

        return ResponseResult.okResult();
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

    private boolean phoneExist(String phoneNumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhoneNumber,phoneNumber);
        count(queryWrapper);
        //大于0表示存在
        return count(queryWrapper)>0;
    }
}


