package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.CategoryDto;
import com.archine.domain.dto.CategoryStatusDto;
import com.archine.domain.entity.Category;
import com.archine.domain.vo.CategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-06 00:47:57
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

     List<CategoryVo> listAllCategory();

    ResponseResult list(Integer pageNum, Integer pageSize, CategoryDto categoryDto);

    ResponseResult addCategory(CategoryDto categoryDto);

    ResponseResult getCategory(Long id);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult deleteCategory(List<Long> id);

    ResponseResult changeStatus(CategoryStatusDto categoryStatusDto);
}


