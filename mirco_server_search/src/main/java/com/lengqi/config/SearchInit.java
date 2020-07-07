package com.lengqi.config;

import com.lengqi.service.ISearchService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SearchInit implements CommandLineRunner {
    @Resource
    private ISearchService searchService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("启动SpringBoot时触发。。");
        //判断索引是否存在，如果不存在，就创建缩影
        if (!searchService.isIndex()){
            searchService.createIndex();
            searchService.addMapping();
            System.out.println("映射关系创建成功！");
        }
    }
}
