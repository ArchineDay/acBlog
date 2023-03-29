package com.archine.controller;

import com.archine.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.archine.domain.ResponseResult;


@RestController
@RequestMapping("/article")
@Api(tags = "文章接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

       return articleService.hotArticleList();

    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);

    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable ("id") Long id){
        return articleService.getArticleDetail(id);
    }

    @PostMapping("/updateViewCount/{id}")
    //PathVariable注解表示将路径中的id参数绑定到方法的参数id上
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

}
