package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    //RequestParam 用于获取表单中的数据
    public ResponseResult upload(@RequestParam("img") MultipartFile img) {
        return uploadService.uploadImg(img);
    }
}
