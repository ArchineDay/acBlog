package com.archine.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.archine.domain.ResponseResult;
import com.archine.domain.entity.Category;
import com.archine.domain.vo.CategoryVo;
import com.archine.domain.vo.ExcelCategoryVo;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.service.CategoryService;
import com.archine.utils.BeanCopyUtils;
import com.archine.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.WeakHashMap;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> categoryVos = categoryService.listAllCategory();
        return ResponseResult.okResult(categoryVos);
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        try {
            //设置下载的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要的数据
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).sheet("分类导出").doWrite(excelCategoryVos);
        } catch (Exception e) {
            //如果出现异常返回响应json
            ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(errorResult));

        }



    }

}
