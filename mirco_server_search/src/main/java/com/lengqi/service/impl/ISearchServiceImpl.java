package com.lengqi.service.impl;

import com.alibaba.fastjson.JSON;
import com.lengqi.entity.Hotel;
import com.lengqi.entity.HotelEvent;
import com.lengqi.entity.SearchParams;
import com.lengqi.service.ISearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


@Service
public class ISearchServiceImpl implements ISearchService {
    @Resource
    private RestHighLevelClient client;
    public static final String INDEX_NAME = "hotel_index";
    @Override
    public boolean createIndex() throws IOException {
        //创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        Map<String, Object> map = new HashMap<>();
        map.put("number_of_shards",1);
        map.put("number_of_replicas",0);
        request.settings(map);
      // 2、客户端执行请求 IndicesClient,请求后获得响应

        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean isIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX_NAME);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    @Override
    public boolean deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        return delete.isAcknowledged();
    }
    /**
     * 添加映射类型 - 创建表结构
     *
     * PUT /test_index/_mapping/hotal
     * {
     *   "properties": {
     *     "hotalName":{
     *       "type": "text",
     *       "analyzer": "ik_max_word"
     *     },
     *     "hotalInfo":{
     *       "type": "text",
     *       "analyzer": "ik_max_word"
     *     },
     *     "hotalImage":{
     *       "type": "keyword",
     *       "index": false
     *     },
     *     "price":{
     *       "type": "scaled_float",
     *       "scaling_factor": 100
     *     },
     *     "openTime":{
     *       "type": "date",
     *       "format": "yyyy-MM-dd"
     *     },
     *     "location":{
     *       "type": "geo_point"
     *     },
     *     "cityName":{
     *       "type": "keyword"
     *     }
     *   }
     * }
     *
     *
     * @return
     */
    @Override
    public boolean addMapping() throws IOException {
        PutMappingRequest request = new PutMappingRequest(INDEX_NAME);
        XContentBuilder builder = JsonXContent.contentBuilder();
        //startObject相当于大括号的    {
        //endObject相当于大括号的  }
        //field就是属性
        builder
                .startObject()
                .startObject("properties")

                .startObject()
                .startObject("properties")

                .startObject("hotalName")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("hotalImage")
                .field("type", "keyword")
                .field("index", "false")
                .endObject()

                .startObject("type")
                .field("type", "integer")
                .endObject()

                .startObject("hotalInfo")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("keyword")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("location")
                .field("type", "geo_point")
                .endObject()

                .startObject("star")
                .field("type", "integer")
                .endObject()

                .startObject("brand")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("address")
                .field("type", "text")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("openTime")
                .field("type", "date")
                .field("format", "yyyy-MM-dd")
                .endObject()

                .startObject("cityname")
                .field("type", "keyword")
                .endObject()

                .startObject("price")
                .field("type", "double")
                .endObject()

                .startObject("roomNumber")
                .field("type", "integer")
                .endObject()

                .startObject("djl")
                .field("type", "integer")
                .endObject()

                .startObject("gzl")
                .field("type", "integer")
                .endObject()

                .endObject()
                .endObject();
        request.source(builder);
        AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    @Override
    public boolean addDocument(Hotel hotel) throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME,"_doc",hotel.getId()+"");

        // 规则 put /kuang_index/_doc/1
        // request.id("1");
        // request.timeout(TimeValue.timeValueSeconds(1));
        // request.timeout("1s")

        // String json = "{'hotalName':xxxx, 'hotalInfo':xxxx, 'xxxx': xxxx}";
        String jsonString = JSON.toJSONString(hotel);
        indexRequest.source(jsonString, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        return response.getShardInfo().getSuccessful() == 1;
    }
    /**
     * 通过查询条件，执行相应的查询
     * # 关键字，（品牌、标题、行政区域、标签、酒店简介）
     * # 城市名称，-优先匹配当前城市的酒店，其他城市的酒店优先级降低
     * GET hotel_index/_search
     * {
     *   "query": {
     *     "boosting": {
     *       "positive": {
     *         "bool": {
     *           "must": [
     *             {
     *               "multi_match": {
     *                 "query": "天河",
     *                 "fields": ["brand","hotalInfo","hotalName","regid","keyword"]
     *               }
     *             },
     *             {
     *               "range": {
     *                 "price": {
     *                   "gte": 100,
     *                   "lte": 300
     *                 }
     *               }
     *             }
     *           ]
     *         }
     *       },
     *       "negative": {
     *         "bool": {
     *           "must_not": [
     *             {
     *               "term": {
     *                 "cityname": {
     *                   "value": "广州"
     *                 }
     *               }
     *             }
     *           ]
     *         }
     *       },
     *       "negative_boost": 0.2
     *     }
     *   }
     * }
     * @param searchParams
     * @return
     * @throws IOException
     */
    @Override
    public List<Hotel> query(SearchParams searchParams) throws IOException {
        //由里向外写，比较清晰

        //negative的bool的城市查询，表示除了什么其他城市
        QueryBuilder cityQuery = QueryBuilders.termQuery("cityname",searchParams.getCityname());

        //通过关键字匹配多个字段
        //判断关键字是否非空
        QueryBuilder keyword = null;
        if (StringUtils.isNotEmpty(searchParams.getKeyword())){
              QueryBuilders.multiMatchQuery(searchParams.getKeyword())
                    .field("brand", 2.0f)
                    .field("hotalName", 2.0f)
                    .field("regid")
                    .field("keyword")
                    .field("hotalInfo");
        }else{
            //等于空，则按定位的城市来搜索
            keyword = cityQuery;
        }

        //匹配价格
        RangeQueryBuilder queryBuilder1 = QueryBuilders.rangeQuery("price")
                .gte(searchParams.getMinPrice() != null ? searchParams.getMinPrice():0)
                .lte(searchParams.getMaxPrice() != null ? searchParams.getMaxPrice():Integer.MAX_VALUE);
        //bool里面的must包含mutilmatch和range
        assert keyword != null;
        BoolQueryBuilder must = QueryBuilders.boolQuery().must(keyword).must(queryBuilder1);

        BoolQueryBuilder mustNot = QueryBuilders.boolQuery().mustNot(cityQuery);
        //boosting里面的positive和negative
        BoostingQueryBuilder boost = QueryBuilders.boostingQuery(must, mustNot).negativeBoost(0.2f);
        SortBuilder sortBuilder = null;
        switch (searchParams.getSortType()){
            case 1:
                break;
            case 2:
                //价格排序，默认升序
                SortBuilders.fieldSort("price").order(SortOrder.ASC);
                break;
            case 3:
                //距离排序，
                sortBuilder = SortBuilders.geoDistanceSort("location",searchParams.getLat(),searchParams.getLon())
                        .unit(DistanceUnit.KILOMETERS)
                        .order(SortOrder.ASC);
                break;

        }
        SearchResponse query = query(boost, sortBuilder);
        long totalHits = query.getHits().totalHits;
        System.out.println("查询的总条数："+totalHits);
        SearchHit[] hits = query.getHits().getHits();

        List<Hotel> list = new ArrayList<>();
        Arrays.stream(hits).forEach(hit -> {
            //一个信息就是一个文档，也就是一个酒店
            System.out.println("酒店信息：" + hit.getSourceAsString());
            //现在是json格式
            String asString = hit.getSourceAsString();
            //将json格式转成hotel对象
            Hotel hotel = JSON.parseObject(asString, Hotel.class);
            if (searchParams.getSortType() == 3){
                //拿到距离的值
                Double juli =(Double) hit.getSortValues()[0];
                System.out.println("距离：" + juli);
                //将距离四舍五日
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                hotel.setJuli(decimalFormat.format(juli)+"km");
            }

            list.add(hotel);
        });
        return list;
    }

    @Override
    public boolean updateDoc(HotelEvent hotelEvent) throws IOException {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest(INDEX_NAME);
        //根据酒店id查询酒店文档，后面的脚本修改就回修改这些查询出来的文档
        updateByQueryRequest.setQuery(QueryBuilders.idsQuery().addIds(hotelEvent.getHid()+""));


        //设置修改的脚本，自增操作 - 脚本
        String script = null;

        if (hotelEvent.getEvent() == 1){
            script = "ctx._source.djl=ctx._source.djl+1";
        }else if (hotelEvent.getEvent() == 2){
            script = "ctx._source.gzl=ctx._source.gzl+1";
        }
        //设置修改的脚本，自增操作 - 脚本
        updateByQueryRequest.setScript(new Script(script));

        //id为16的酒店，点击率自增,根据查询出来的文档更新
        BulkByScrollResponse response = client.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        return response.getUpdated() > 0;
    }

    private SearchResponse query(QueryBuilder queryBuilder, SortBuilder sortBuilder) throws IOException{
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(queryBuilder);
        //如果排序构建起不等于空，则构建排序构建起
        if (sortBuilder != null){
            sourceBuilder.sort(sortBuilder);
        }
        //设置查询构建起器
        searchRequest.source(sourceBuilder);

        //执行查询
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        return response;
    }
}
