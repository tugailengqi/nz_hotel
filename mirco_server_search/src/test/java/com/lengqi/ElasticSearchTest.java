package com.lengqi;

import com.lengqi.entity.Hotel;
import com.lengqi.service.ISearchService;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTest {
    @Resource
    private ISearchService searchService;
    @Test
    public void test1() throws IOException {
        if (!searchService.isIndex()){
            //不存在就创建索引
            System.out.println("创建了索引！！！");
            searchService.createIndex();
            //创建映射关系
            searchService.addMapping();
            System.out.println("映射关系创建成功！");
            //
        }else {
            System.out.println("索引库已经存在！！！！");
        }
    }
    @Test
    public void addDoc() throws IOException {
        Hotel hotel1 = new Hotel()
                .setCid(1)
                .setHotalName("7天连锁快捷酒店")
                .setHotalInfo("性价比很高的酒店！")
                .setPrice(BigDecimal.valueOf(298.98))
                .setLat(22.566346)
                .setLon(114.066852)
                .setBrand("7天");
        Hotel hotel2 = new Hotel()
                .setId(2)
                .setHotalName("7天优品酒店！")
                .setHotalInfo("性价比很高的酒店！")
                .setPrice(BigDecimal.valueOf(298.98))
                .setLat(22.466346)
                .setLon(114.166852)
                .setBrand("7天");

        Hotel hotel3 = new Hotel()
                .setId(3)
                .setHotalName("希尔顿星级酒店！")
                .setHotalInfo("很贵的酒店！")
                .setPrice(BigDecimal.valueOf(1298.98))
                .setLat(22.366346)
                .setLon(114.266852)
                .setBrand("希尔顿");

        Hotel hotel4 = new Hotel()
                .setId(4)
                .setHotalName("速8连锁快捷酒店！")
                .setHotalInfo("很便宜的酒店！")
                .setPrice(BigDecimal.valueOf(198.98))
                .setLat(22.866346)
                .setLon(114.266852)
                .setBrand("速8");

        Hotel hotel5 = new Hotel()
                .setId(5)
                .setHotalName("维也纳高端酒店！")
                .setHotalInfo("很便宜的酒店！")
                .setPrice(BigDecimal.valueOf(598.98))
                .setLat(22.166346)
                .setLon(114.866852)
                .setBrand("维也纳");

        Hotel hotel6 = new Hotel()
                .setId(6)
                .setHotalName("大中华国际酒店！")
                .setHotalInfo("很厉害的酒店！")
                .setPrice(BigDecimal.valueOf(2598.98))
                .setLat(22.566346)
                .setLon(114.266852)
                .setBrand("大中华");

        Hotel hotel7 = new Hotel()
                .setId(7)
                .setHotalName("全球连锁大酒店！")
                .setHotalInfo("很厉害的酒店！")
                .setPrice(BigDecimal.valueOf(2598.98))
                .setLat(22.166346)
                .setLon(115.066852)
                .setBrand("大中华");

        Hotel hotel8= new Hotel()
                .setId(8)
                .setHotalName("全国连锁大酒店！")
                .setHotalInfo("很厉害的酒店！")
                .setPrice(BigDecimal.valueOf(1598.98))
                .setLat(22.156346)
                .setLon(114.126852)
                .setBrand("如家酒店");

        Hotel hotel9= new Hotel()
                .setId(9)
                .setHotalName("品牌连锁酒店")
                .setHotalInfo("很厉害性价比很高的大中华酒店！")
                .setPrice(BigDecimal.valueOf(1598.98))
                .setLat(22.346346)
                .setLon(114.356852)
                .setBrand("大中华");

        searchService.addDocument(hotel1);
        searchService.addDocument(hotel2);
        searchService.addDocument(hotel3);
        searchService.addDocument(hotel4);
        searchService.addDocument(hotel5);
        searchService.addDocument(hotel6);
        searchService.addDocument(hotel7);
        searchService.addDocument(hotel8);
        searchService.addDocument(hotel9);
    }
    @Test
    public void search() throws IOException {
        //term查询
        TermQueryBuilder termQuery = QueryBuilders.termQuery("brand", "如家酒店");
        //match查询
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("hotalName", "优品酒店");
        //多字段，mutil-match
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("大中华")
                .field("hotalName", 2.0f).field("hotalInfo", 1.0f);


//        searchService.query(termQuery);
//        searchService.query(matchQuery);
//        searchService.query(multiMatchQueryBuilder);
    }
}
