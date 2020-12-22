package com.zhitong.mytestserver.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhitong.feignserver.es.entity.User;
import com.zhitong.feignserver.es.service.EsCrudService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : subs
 * @Project: sbs-parent
 * @Package com.zhitong.feignserver.es.service.impl
 * @Description:
 * @date Date : 2020年11月23日 13:41
 */
@Service
public class EsCrudServiceImpl implements EsCrudService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String index = "su-2020";

    private static final String type = "deme";

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引并添加映射关系
     */
    @Override
    public void createIndex(){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("su-2021");
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("properties")
                    .startObject("name").field("type", "text").field("index", true).endObject()
                    .startObject("age").field("type", "integer").field("index", true).endObject()
                    .startObject("sex").field("type", "text").field("index", true).endObject()
                    .startObject("address").field("type", "text").field("index", true).endObject()
                    .startObject("birthday").field("type", "date").field("index", true).endObject()
                    .endObject().endObject();
            createIndexRequest.mapping(builder);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            if (acknowledged) {
                logger.info("创建索引成功");
            } else {
                logger.info("创建索引失败");
            }
        } catch (IOException e) {
           logger.error("创建失败");
        }

        //查看索引是否存在
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest("su-2021");
            getIndexRequest.humanReadable(true);
            Boolean exists = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
            if (exists){
                logger.info("索引已存在");
            }else {
                logger.info("索引不存在");
            }
        } catch (IOException e) {
            logger.error("查询失败");
        }
    }

    /**
     * 新增数据
     */
    @Override
    public void saveDate(){
        User user = new User();
        user.setName("zhangsan");
        user.setAge(20);
        user.setSex("man");
        user.setAddress("nanjing,china");
        String userInfo = JSONObject.toJSONString(user);
        IndexRequest indexRequest = new IndexRequest(index,type,"1");
        indexRequest.source(userInfo, XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            logger.info("往es新增数据");
        } catch (IOException e) {
            logger.error("新增数据失败:{}",e.toString());
        }
    }

    /**
     * 查询数据
     */
    @Override
    public void queryDate() {
        try {
            GetRequest getRequest = new GetRequest(index,type,"1");
            GetResponse documentFields = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            logger.info("查询结果{}", JSON.toJSONString(documentFields));
        } catch (IOException e) {
            logger.error("查询结果失败:{}",e.toString());
        }
    }
}
