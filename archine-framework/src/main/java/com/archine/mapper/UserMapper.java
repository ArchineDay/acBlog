package com.archine.mapper;

import com.archine.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-13 13:50:08
 */
public interface UserMapper extends BaseMapper<User> {

    void findRoleIdsByUserId(Long id);
}


