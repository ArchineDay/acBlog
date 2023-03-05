package com.archine.utils;

import com.archine.domain.entity.Article;
import com.archine.domain.vo.HotArticleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;

import java.util.List;


class BeanCopyUtilsTest {

    @Test
    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("1233");
        article.setViewCount(1122L);
        article.setStatus("1");

        HotArticleVo hotArticleVo = BeanCopyUtils.copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);

    }


}