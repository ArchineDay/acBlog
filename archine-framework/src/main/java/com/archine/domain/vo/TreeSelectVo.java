package com.archine.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeSelectVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String label;
    //父菜单ID
    private Long parentId;

    private List<TreeSelectVo> Children;
}
