package com.archine.service;

import com.archine.domain.ResponseResult;
import com.archine.domain.dto.TagListDto;
import com.archine.domain.entity.Tag;
import com.archine.domain.vo.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-29 17:48:12
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);
    

    ResponseResult deleteTag(Long id);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(Tag tag);

    ResponseResult listAllTag();
}


