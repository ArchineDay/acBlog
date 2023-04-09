package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Comment;
import com.archine.domain.vo.CommentVo;
import com.archine.domain.vo.PageVo;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.exception.SystemException;
import com.archine.mapper.CommentMapper;
import com.archine.service.CommentService;
import com.archine.service.UserService;
import com.archine.utils.BeanCopyUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //article评论时对articleId进行判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_NORMAL);
        //评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //查询所有根评论的子评论的集合，并赋值给对应属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children=getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        //根据根评论id查询子评论集合
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;


    }

    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //通过createBy查询本用户的昵称并赋值
            //如果用户不存在则显示用户不存在
            if (userService.getById(commentVo.getCreateBy())==null){
                commentVo.setUsername("用户不存在");
            }else {
                String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
                commentVo.setUsername(nickName);
            }

            //通过toCommentUserId查询回复用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(!commentVo.getToCommentUserId().equals(-1L)){
                //判断回复的用户是否存在
                if(userService.getById(commentVo.getToCommentUserId())==null){
                    commentVo.setToCommentUserName("用户不存在");
                }
                else {
                    String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                    commentVo.setToCommentUserName(toCommentUserName);
                }
            }
        }
        return commentVos;
    }
}


