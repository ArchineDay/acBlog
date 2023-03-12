package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-12 14:02:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}


