package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.dto.LinkDto;
import com.archine.domain.entity.Link;
import com.archine.domain.vo.LinkVo;
import com.archine.domain.vo.PageVo;
import com.archine.mapper.LinkMapper;
import com.archine.service.LinkService;
import com.archine.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-12 14:02:48
 */
@Service("linkService")

public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成vo
        List<LinkVo> LinkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(LinkVos);
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, LinkDto linkDto) {
        //查询条件
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkDto.getName()), Link::getName, linkDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkDto.getStatus()), Link::getStatus, linkDto.getStatus());
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        //转换成vo
        List<LinkVo> LinkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);
        PageVo pageVos = new PageVo(LinkVos, page.getTotal());

        return ResponseResult.okResult(pageVos);
    }

    @Override
    public ResponseResult addList(LinkDto linkDto) {
        //转换成实体类
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtils.copyBean(link, LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult updateLink(LinkDto linkDto) {
        //转换成实体类
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(List<Long> id) {
        removeByIds(id);
        return ResponseResult.okResult();
    }


}


