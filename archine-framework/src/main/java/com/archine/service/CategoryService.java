package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 00:47:57
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}


