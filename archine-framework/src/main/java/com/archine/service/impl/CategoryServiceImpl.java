package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.dto.CategoryDto;
import com.archine.domain.dto.CategoryStatusDto;
import com.archine.domain.entity.Article;
import com.archine.domain.entity.Category;
import com.archine.domain.vo.CategoryVo;
import com.archine.domain.vo.PageVo;
import com.archine.mapper.CategoryMapper;
import com.archine.service.ArticleService;
import com.archine.service.CategoryService;
import com.archine.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-06 00:47:58
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已发布的文章
        LambdaQueryWrapper <Article> articleWrapper=new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);

        //根据文章分类id 并且去重
        Set<Long> categoryIds  = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
//        Set<Long> categoryIds = articleList.stream()
//                .map(new Function<Article, Long>() {
//                    @Override
//                    public Long apply(Article article) {
//                        return article.getCategoryId();
//                    }
//                })
//                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        //必须是正常状态的分类
        categories = categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
//        List<Category> categories = listByIds(categoryIds);
//         categories = categories.stream().
//                filter(new Predicate<Category>() {
//                    @Override
//                    public boolean test(Category category) {
//                        return category.getStatus().equals(SystemConstants.STATUS_NORMAL);
//                    }
//                }).collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, SystemConstants.STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, CategoryDto categoryDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryDto.getName()),Category::getName, categoryDto.getName());
        queryWrapper.eq(StringUtils.hasText(categoryDto.getStatus()),Category::getStatus, categoryDto.getStatus());
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);
        List<Category> list = page.getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        PageVo pageVo = new PageVo(categoryVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategory(Long id) {
        Category category = getById(id);
        CategoryVo categoryVo = BeanCopyUtils.copyBean(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVo);
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(List<Long> id) {
        removeByIds(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(CategoryStatusDto categoryStatusDto) {
        Long categoryId = categoryStatusDto.getCategoryId();
        String status = categoryStatusDto.getStatus();
        Category category = getById(categoryId);
        category.setStatus(status);
        updateById(category);
        return ResponseResult.okResult();
    }
}


