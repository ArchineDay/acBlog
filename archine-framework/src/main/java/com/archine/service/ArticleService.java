package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.AddArticleDto;
import com.archine.domain.dto.ArticleListDto;
import com.archine.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
    ResponseResult getArticleDetail(Long id);
    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto article);

    ResponseResult getArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);
}
