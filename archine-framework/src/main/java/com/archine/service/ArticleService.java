package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.AddArticleDto;
import com.archine.domain.dto.ArticleListDto;
import com.archine.domain.entity.Article;
import com.archine.domain.vo.ArticleVo;
import com.archine.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
    ResponseResult getArticleDetail(Long id);
    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto article);

    ResponseResult<PageVo> getArticleList(Integer pageNum, Integer pageSize, ArticleListDto articleListDto);

    ResponseResult articleDetail(Long id);

    ResponseResult updateArticle(AddArticleDto articleDto);

    ResponseResult deleteArticle(List<Long> id);
}
