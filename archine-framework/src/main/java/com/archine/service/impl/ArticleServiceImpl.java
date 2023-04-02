package com.archine.service.impl;

import com.archine.constants.SystemConstants;
import com.archine.domain.ResponseResult;
import com.archine.domain.dto.AddArticleDto;
import com.archine.domain.entity.Article;
import com.archine.domain.entity.ArticleTag;
import com.archine.domain.entity.Category;
import com.archine.domain.vo.ArticleDetailVo;
import com.archine.domain.vo.ArticleListVo;
import com.archine.domain.vo.HotArticleVo;
import com.archine.domain.vo.PageVo;
import com.archine.mapper.ArticleMapper;
import com.archine.service.ArticleService;
import com.archine.service.ArticleTagService;
import com.archine.service.CategoryService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        Map<String, Integer> viewCount = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT);
            //将map转换成list
        /**
         * 使用entrySet()方法将Map中的键值对转换为Set<Map.Entry<String, Integer>>对象。
         * 使用stream()方法将Set对象转换为Stream<Map.Entry<String, Integer>>对象。
         * 使用map()方法将Stream<Map.Entry<String, Integer>>对象中的每个元素（即键值对）转换为一个Article对象。
         * 使用了lambda表达式编写转换逻辑，将键转换为文章的ID，将值转换为文章的浏览量。map()方法的返回值是一个Stream<Article>对象。
         * 使用collect()方法将Stream<Article>对象转换为List<Article>对象，并将其保存在articles变量中。
         */
            List<Article> articles = viewCount.entrySet().stream()
                    .map(entry -> {
                        Article article = new Article();
                        article.setId(Long.parseLong(entry.getKey()));
                        article.setViewCount(Long.valueOf(entry.getValue()));
                        return article;
                    }).collect(Collectors.toList());
            //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //分页只查询10条
        Page<Article> page= new Page(1,10);
        page(page,queryWrapper);

      //  articles = page.getRecords();

        //bean拷贝
//        List<HotArticleVo> articleVos=new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);

    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //如果有categoryId,他的查询值和传入的值要相同
         lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正常发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName和viewCount

        List<Article> articles = page.getRecords();
        Map<String, Integer> viewCount = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT);
        /**
         * 使用stream()方法将articles列表转换为一个Stream<Article>对象。
         * 使用map()方法将Stream<Article>对象中的每个元素（即文章）转换为另一个Article对象。
         * 使用一个lambda表达式来设置文章的类别名称和浏览量属性，并将修改后的article对象返回。
         */
        articles.stream()
                .map( article -> {
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    article.setViewCount(Long.valueOf(viewCount.get(article.getId().toString())));
                    return article;
                })
                .collect(Collectors.toList());
        //articleId去查询articleName
//        for (Article article : articles) {
//            //拿article表中的id去category中找对应的name
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //封装查询结果为vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询对应分类名称
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (categoryId!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult addArticle(AddArticleDto articleDto) {
        //添加博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);

        //添加博客和标签关联
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

}
