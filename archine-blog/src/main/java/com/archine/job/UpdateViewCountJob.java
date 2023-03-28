package com.archine.job;

import com.archine.constants.SystemConstants;
import com.archine.domain.entity.Article;
import com.archine.service.ArticleService;
import com.archine.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob{
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/60 * * * * ?")
    public void updateViewCount(){
        //获取Redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT);

        //q：为什么用entrySet()而不是keySet()？
        //a：因为keySet()只能获取到key，而entrySet()可以获取到key和value
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                //为什么要用.map()？因为entrySet()返回的是Set<Map.Entry<K,V>>，而我们需要的是List<Article>
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        //q：为什么要用updateBatchById()而不是updateById()？
        //a：因为updateById()只能更新一条数据，而updateBatchById()可以更新多条数据
        articleService.updateBatchById(articles);

    }
}
