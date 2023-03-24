package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService{
    ResponseResult uploadImg(MultipartFile img);
}
