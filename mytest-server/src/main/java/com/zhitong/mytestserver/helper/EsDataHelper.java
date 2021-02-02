package com.zhitong.mytestserver.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class EsDataHelper {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 新增/修改数据
     *
     * @param indexName
     * @param json
     * @param idProperty
     * @throws IOException
     */
    public void indexData(String indexName, JSONObject json, String idProperty) {
        IndexRequest request = new IndexRequest(indexName).id(json.get(idProperty).toString())
                .source(json, XContentType.JSON);
        //立即刷新
        //request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);

            log.info("indexData response:{}", indexResponse);
        } catch (IOException ioe) {
            log.error("indexData error:", ioe);
            throw new RuntimeException("数据保存失败");
        }
    }

    /**
     * 根据ID获取数据
     *
     * @param indexName 索引
     * @param id        id
     * @return
     */
    public JSONObject getById(String indexName, String id) {
        GetRequest getRequest = new GetRequest(indexName, id);
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            log.info("getById response:{}", getResponse);
            if (getResponse.isExists()) {
                String sourceAsString = getResponse.getSourceAsString();
                return JSONObject.parseObject(sourceAsString);
            } else {
                return null;
            }
        } catch (IOException ioe) {
            log.error("get error:", ioe);
            throw new RuntimeException("数据查询失败");
        }
    }

    /**
     * 删除
     *
     * @param indexName
     * @param id
     * @return
     */
    public void deleteById(String indexName, String id) {
        DeleteRequest request = new DeleteRequest(indexName, id);
        //request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            log.info("deleteById response:{}", deleteResponse);
        } catch (IOException ioe) {
            log.error("deleteById error:", ioe);
            throw new RuntimeException("数据删除失败");
        }
    }

    /**
     * 更新数据
     *
     * @param indexName
     * @param id
     * @param json
     */
    public void update(String indexName, String id, JSONObject json) {
        UpdateRequest request = new UpdateRequest(indexName, id);
        //request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        request.doc(json.toJSONString(), XContentType.JSON);
        try {
            UpdateResponse updateResponse = restHighLevelClient.update(
                    request, RequestOptions.DEFAULT);
            log.info("update response:{}", updateResponse);
        } catch (IOException ioe) {
            log.error("update error:", ioe);
            throw new RuntimeException("数据更新失败");
        }
    }

    /**
     * 批量新增/修改数据
     *
     * @param indexName
     * @param jsonArray
     * @param idProperty
     * @throws IOException
     */
    public void indexDataBatch(String indexName, JSONArray jsonArray, String idProperty) {
        BulkRequest bulkRequest = new BulkRequest();
        //bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            IndexRequest request = new IndexRequest(indexName).id(jsonObject.get(idProperty).toString())
                    .source(jsonObject.toJSONString(), XContentType.JSON);
            bulkRequest.add(request);
        }
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("indexDataBatch response:{}", bulkResponse);
        } catch (IOException ioe) {
            log.error("indexDataBatch error:", ioe);
            throw new RuntimeException("数据保存失败");
        }
    }


    /**
     * 批量删除
     *
     * @param indexName
     * @param ids       id列表
     */
    public void deleteBatch(String indexName, List<Long> ids) {
        try {
            BulkRequest bulkRequest = new BulkRequest();
            //bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            ids.forEach(id -> {
                DeleteRequest request = new DeleteRequest(indexName, id.toString());
                bulkRequest.add(request);
            });
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            log.info("deleteBatch response:{}", bulkResponse);
        } catch (IOException ioe) {
            log.error("deleteBatch error:", ioe);
            throw new RuntimeException("批量删除数据失败");
        }
    }


    /**
     * 多个查询
     *
     * @param indexName
     * @param ids
     * @return
     */
    public JSONArray multiGet(String indexName, List<Long> ids) {
        try {
            JSONArray result = new JSONArray();
            MultiGetRequest request = new MultiGetRequest();
            ids.forEach(id -> {
                request.add(new MultiGetRequest.Item(
                        indexName,
                        id.toString()));
            });
            MultiGetResponse response = restHighLevelClient.mget(request, RequestOptions.DEFAULT);
            log.info("multiGet response:{}", response);
            MultiGetItemResponse[] datas = response.getResponses();
            for (MultiGetItemResponse mgip : datas) {
                JSONObject jsonObject = JSONObject.parseObject(mgip.getResponse().getSourceAsString());
                result.add(jsonObject);
            }
            return result;
        } catch (IOException ioe) {
            log.error("multiGet error:", ioe);
            throw new RuntimeException("批量查询数据失败");
        }
    }

    /**
     * reIndex
     *
     * @param fromIndexs
     * @param toIndex
     */
    public void reIndex(String[] fromIndexs, String toIndex) {
        try {
            ReindexRequest request = new ReindexRequest();
            request.setSourceIndices(fromIndexs);
            request.setDestIndex(toIndex);
            BulkByScrollResponse bulkResponse =
                    restHighLevelClient.reindex(request, RequestOptions.DEFAULT);
            log.info("reIndex response:{}", bulkResponse);
        } catch (IOException ioe) {
            log.error("reIndex error:", ioe);
            throw new RuntimeException("数据迁移失败");
        }
    }

}
