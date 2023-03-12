package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Link;
import com.archine.domain.vo.LinkVo;
import com.archine.mapper.LinkMapper;
import com.archine.service.LinkService;
import com.archine.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        List<LinkVo> LinkVos = BeanCopyUtils.copuBeanList(links, LinkVo.class);
        //封装返回

        return ResponseResult.okResult(LinkVos);
    }
}


