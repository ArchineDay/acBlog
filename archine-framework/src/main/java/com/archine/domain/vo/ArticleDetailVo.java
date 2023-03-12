package com.archine.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //分类id
    private Long categoryId;
    //所属分类名
    private String categoryName;
    //缩略图
    private String status;
    //访问量
    private Long viewCount;
    //创建时间
    private Date createTime;
    //文章内容
    private String content;

}
