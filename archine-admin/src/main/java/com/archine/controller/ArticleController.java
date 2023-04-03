package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.AddArticleDto;
import com.archine.domain.dto.ArticleListDto;
import com.archine.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto article){
        return articleService.addArticle(article);
    }

    @GetMapping("/list")
    public ResponseResult getArticleListByTS(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){

        return articleService.getArticleList(pageNum,pageSize,articleListDto);
    }
}
