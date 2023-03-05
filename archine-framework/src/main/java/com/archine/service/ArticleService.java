package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
}
