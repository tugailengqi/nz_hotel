package com.lengqi.service;

import com.lengqi.entity.Hotel;
import com.lengqi.entity.HotelEvent;
import com.lengqi.entity.SearchParams;
import org.elasticsearch.index.query.QueryBuilder;

import java.io.IOException;
import java.util.List;

public interface ISearchService {
    /**
     * 创建索引库
     */
    boolean createIndex() throws IOException;

    /**
     * 判断索引库是否存在
     */
    boolean isIndex() throws IOException;

    /**
     * 删除索引库
     */
    boolean deleteIndex() throws  IOException;

    /**
     * 添加索引映射
     */
    boolean addMapping() throws IOException;

    /**
     * 添加文档
     */
    boolean addDocument(Hotel hotel) throws IOException;

    /**
     * 查询
     * @param searchParams
     * @return
     * @throws IOException
     */
     List<Hotel> query(SearchParams searchParams) throws IOException;

    /**
     * 修改文档信息
     * @param hotelEvent
     * @return
     */
     boolean updateDoc(HotelEvent hotelEvent) throws IOException;
}
