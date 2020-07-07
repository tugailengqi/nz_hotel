package com.lengqi.controller;

import com.lengqi.entity.Hotel;
import com.lengqi.entity.ResultData;
import com.lengqi.entity.SearchParams;
import com.lengqi.service.ISearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private ISearchService searchService;
    /**
     * 酒店列表
     * @return
     */
    @RequestMapping("/searchList")
    public ResultData<List<Hotel>> searchHotels(SearchParams params){
        System.out.println("接收到搜索条件："+params);
        try {
            List<Hotel> query = searchService.query(params);
            return new ResultData<List<Hotel>>().setCode(ResultData.Code.CODE_SUCC).setData(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultData<List<Hotel>>().setCode(ResultData.Code.CODE_ERROR);
    }
}
