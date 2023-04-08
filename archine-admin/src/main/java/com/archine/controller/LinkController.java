package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.LinkDto;
import com.archine.domain.entity.Link;
import com.archine.domain.vo.LinkVo;
import com.archine.service.LinkService;
import com.archine.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkDto linkDto){
        return linkService.list(pageNum,pageSize,linkDto);
    }
    @PostMapping()
    public ResponseResult addList(@RequestBody LinkDto linkDto){
        return linkService.addList(linkDto);
    }
    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id) {
        return linkService.getLinkById(id);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkDto linkDto){
        return linkService.updateLink(linkDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable List<Long> id ){
        return linkService.deleteLink(id);
    }
}
