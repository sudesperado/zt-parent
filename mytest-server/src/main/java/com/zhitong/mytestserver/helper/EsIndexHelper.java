package com.zhitong.mytestserver.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class EsIndexHelper {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @param mapping   映射
     * @param alias     别名
     */
    public void createIndex(String indexName, String mapping, String alias) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        RequestOptions requestOptions = RequestOptions.DEFAULT;
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        createIndexRequest.mapping(mapping, XContentType.JSON);

        if (StringUtils.isNotBlank(alias)) {
            createIndexRequest.alias(new Alias(alias));
        }

        //可选参数
        createIndexRequest.setTimeout(TimeValue.timeValueMinutes(2));

        //连接master节点的超时时间(使用TimeValue方式)
        createIndexRequest.setMasterTimeout(TimeValue.timeValueMinutes(1));

        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, requestOptions);
            log.info("create index response:{}", createIndexResponse);
        } catch (IOException ioe) {
            log.error("create index error:", ioe);
            throw new RuntimeException("索引创建失败");
        }
    }

    /**
     * 判断index是否存在
     *
     * @param indexName
     * @return
     */
    public boolean isExsit(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            return exists;
        } catch (IOException ioe) {
            log.error("index isExsit error:", ioe);
            throw new RuntimeException("判断索引是否存在异常");
        }
    }

    /**
     * 删除索引
     *
     * @param indexName
     */
    public void deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        request.timeout(TimeValue.timeValueMinutes(2));
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        try {
            AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            log.info("deleteIndex info:{}", deleteIndexResponse);
        } catch (IOException ioe) {
            log.error("deleteIndex error:", ioe);
            throw new RuntimeException("删除索引失败");
        }
    }

    /**
     * 打开索引
     *
     * @param indexName
     */
    public void openIndex(String indexName) {
        OpenIndexRequest request = new OpenIndexRequest(indexName);
        try {
            OpenIndexResponse openIndexResponse = restHighLevelClient.indices().open(request, RequestOptions.DEFAULT);
            log.info("openIndex info:{}", openIndexResponse);
        } catch (IOException ioe) {
            log.error("openIndex error:", ioe);
            throw new RuntimeException("打开索引失败");
        }

    }


    /**
     * 关闭索引
     *
     * @param indexName
     */
    public void closeIndex(String indexName) {
        CloseIndexRequest request = new CloseIndexRequest(indexName);
        try {
            AcknowledgedResponse closeIndexResponse = restHighLevelClient.indices().close(request, RequestOptions.DEFAULT);
            log.info("closeIndex info:{}", closeIndexResponse);
        } catch (IOException ioe) {
            log.error("closeIndex error:", ioe);
            throw new RuntimeException("关闭索引失败");
        }
    }

    /**
     * 获取别名信息
     *
     * @param indexName
     * @return
     */
    public List<String> getAliases(String indexName) {
        try {
            List<String> resList = new ArrayList<>();
            GetAliasesRequest request = new GetAliasesRequest();
            request.indices(indexName);
            GetAliasesResponse response = restHighLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
            log.info("getAliases info:{}", response);
            Map<String, Set<AliasMetaData>> aliasMap = response.getAliases();
            Set<AliasMetaData> aliasSet = aliasMap.get(indexName);
            aliasSet.forEach(aliasMetaData -> {
                resList.add(aliasMetaData.getAlias());
            });
            return resList;
        } catch (IOException ioe) {
            log.error("getAliases error:", ioe);
            throw new RuntimeException("获取别名失败");
        }
    }

    /**
     * 设置别名
     *
     * @param indexName
     * @param alias
     */
    public void alias(String indexName, String alias, boolean removeOldAlias) {
        try {
            IndicesAliasesRequest request = new IndicesAliasesRequest();

            if (removeOldAlias) {
                List<String> oldAlias = getAliases(indexName);
                oldAlias.forEach(old -> {
                    IndicesAliasesRequest.AliasActions aliasRemoveAction =
                            new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions
                                    .Type.REMOVE)
                                    .index(indexName)
                                    .alias(old);
                    request.addAliasAction(aliasRemoveAction);
                });
            }
            IndicesAliasesRequest.AliasActions aliasAction =
                    new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions
                            .Type.ADD)
                            .index(indexName)
                            .alias(alias);
            request.addAliasAction(aliasAction);

            AcknowledgedResponse indicesAliasesResponse =
                    restHighLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
            log.info("alias response:{}", indicesAliasesResponse);
        } catch (IOException ioe) {
            log.error("alias error:", ioe);
            throw new RuntimeException("设置别名失败");
        }
    }


    /**
     * 添加mapping
     *
     * @param indexName
     * @param mapping
     */
    public void putMappings(String indexName, String mapping) {
        PutMappingRequest request = new PutMappingRequest(indexName);
        request.source(mapping, XContentType.JSON);
        try {
            AcknowledgedResponse putMappingResponse = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
            log.info("putMappings info:{}", putMappingResponse);
        } catch (IOException ioe) {
            log.error("putMappings error:", ioe);
            throw new RuntimeException("打开索引失败");
        }
    }

}
