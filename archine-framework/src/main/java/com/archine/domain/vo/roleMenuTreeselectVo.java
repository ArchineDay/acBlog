package com.archine.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class roleMenuTreeselectVo {
    private List<TreeSelectVo> menus;
    private List<Long> checkedKeys;

}
