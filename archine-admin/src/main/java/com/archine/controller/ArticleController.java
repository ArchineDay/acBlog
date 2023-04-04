package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.AddArticleDto;
import com.archine.domain.dto.ArticleListDto;
import com.archine.domain.vo.ArticleVo;
import com.archine.domain.vo.PageVo;
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
    public ResponseResult<PageVo> getArticleListByTS(Integer pageNum, Integer pageSize, ArticleListDto articleListDto){

        return articleService.getArticleList(pageNum,pageSize,articleListDto);
    }

    @GetMapping("{id}")
    public ResponseResult getArticleDetail(@PathVariable Long id){
        return articleService.getArticleDetail(id);
    }


    @PutMapping
    public ResponseResult updateArticle(@RequestBody AddArticleDto articleDto){
        return articleService.updateArticle(articleDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }

}
