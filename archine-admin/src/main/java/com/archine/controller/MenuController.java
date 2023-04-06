package com.archine.controller;

import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Menu;
import com.archine.domain.vo.TreeSelectVo;
import com.archine.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult getList( String status,String menuName){

        return menuService.getList(status,menuName);
    }
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Long id){
        return menuService.getMenu(id);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Long id){
        return menuService.deleteMenu(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        List<TreeSelectVo> treeSelectVos = menuService.treeSelect();
        return ResponseResult.okResult(treeSelectVos) ;
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelect(@PathVariable Long id){
        return menuService.roleMenuTreeSelect(id);
    }
}
