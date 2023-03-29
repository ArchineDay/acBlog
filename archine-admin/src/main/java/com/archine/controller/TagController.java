package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("list")
    public ResponseResult list(){
        return ResponseResult.okResult(tagService.list());
    }
}
