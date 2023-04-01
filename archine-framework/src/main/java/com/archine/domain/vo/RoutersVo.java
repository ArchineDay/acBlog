package com.archine.domain.vo;


import com.archine.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
//data注解会自动生成get set方法
public class RoutersVo {
    private List<MenuVo> menus;

}
