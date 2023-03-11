package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Article;
import com.archine.domain.entity.Category;
import com.archine.domain.vo.ArticleListVo;
import com.archine.domain.vo.HotArticleVo;
import com.archine.domain.vo.PageVo;
import com.archine.mapper.ArticleMapper;
import com.archine.service.ArticleService;
import com.archine.service.CategoryService;
import com.archine.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //分页只查询10条
        Page<Article> page= new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        //bean拷贝
//        List<HotArticleVo> articleVos=new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copuBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);

    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //如果有categoryId,他的查询值和传入的值要相同
         lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正常发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        //articleId去查询articleName
        for (Article article : articles) {
            //拿article表中的id去category中找对应的name
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());

        }
        //封装查询结果为vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copuBeanList(page.getRecords(), ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
