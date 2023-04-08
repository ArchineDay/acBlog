package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.LinkDto;
import com.archine.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-12 14:02:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult list(Integer pageNum, Integer pageSize, LinkDto linkDto);

    ResponseResult addList(LinkDto linkDto);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(LinkDto linkDto);

    ResponseResult deleteLink(List<Long> id );
}


