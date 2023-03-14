package com.archine.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    public static <V> V copyBean(Object source ,Class<V> clazz){
        try {
            //创建目标对象
            V result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source,result);
            //返回结果
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <V,O> List<V> copyBeanList(List<O> list,Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
