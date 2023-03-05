package com.archine.controller;

import com.archine.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.archine.domain.ResponseResult;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

       ResponseResult result=articleService.hotArticleList();
        return result;
    }
//@GetMapping("/list")
//public List<Article> test(){
//    return articleService.list();
//}


}
