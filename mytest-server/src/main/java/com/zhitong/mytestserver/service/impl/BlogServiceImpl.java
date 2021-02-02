package com.zhitong.mytestserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhitong.mytestserver.dao.DicMapper;
import com.zhitong.mytestserver.model.Blog;
import com.zhitong.mytestserver.dao.BlogMapper;
import com.zhitong.mytestserver.model.Dic;
import com.zhitong.mytestserver.model.PageResult;
import com.zhitong.mytestserver.model.Result;
import com.zhitong.mytestserver.model.reqVO.BlogReqVO;
import com.zhitong.mytestserver.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhitong.mytestserver.util.DateUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private DicMapper dicMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String index = "zt_blog";

    private static final String type = "_doc";

    @Override
    public Result saveBlog(Blog blog) {
        boolean b = this.save(blog);
        if (!b){
            return Result.newInstance().filed("保存blog失败");
        }
        return Result.newInstance().success("保存成功",blog);
    }

    @Override
    public Result queryById(Integer id) {
       Map<Integer, String> tagMap = getTagMap();

        Blog blog = blogMapper.selectById(id);
        blog.setTagName(tagMap.getOrDefault(blog.getTagId(),""));
        blog.setCreateTimeStr(DateUtils.format(blog.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
        return Result.newInstance().success("查询成功",blog);
    }

    @Override
    public Result del(List<Integer> idList) {
        boolean b = this.removeByIds(idList);
        if (!b){
            return Result.newInstance().filed("删除blog失败");
        }
        return Result.newInstance().success("删除成功",idList);
    }

    @Override
    public Result queryPage(BlogReqVO blogReqVO) {
        Map<Integer, String> tagMap = getTagMap();
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.like(StringUtils.isNotEmpty(blogReqVO.getTitle()),"title",blogReqVO.getTitle());
        blogQueryWrapper.eq(blogReqVO.getTagId()!=null,"tag_id",blogReqVO.getTagId());
        Page<Blog> blogPage = blogMapper.selectPage(new Page<Blog>(blogReqVO.getCurrentPage(), blogReqVO.getPageSize(), true), blogQueryWrapper);
        List<Blog> blogList = Lists.newArrayList();
        for (Blog blog : blogPage.getRecords()) {
            blog.setTagName(tagMap.getOrDefault(Integer.valueOf(blog.getTagId()),""));
            blog.setCreateTimeStr(DateUtils.format(blog.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            blogList.add(blog);
        }
        PageResult<Blog> blogPageResult = new PageResult<>(blogList, (int) blogPage.getTotal(), (int) blogPage.getSize(), (int) blogPage.getCurrent());
        return Result.newInstance().success("查询成功",blogPageResult);
    }

    @Override
    public Result upload(Blog blog) {
        blog.setId(IdWorker.getId());
        blog.setCreateTime(new Date());
        String blogStr = JSONObject.toJSONString(blog);
        IndexRequest indexRequest = new IndexRequest(index).type(type).id(blog.getId().toString());
        indexRequest.source(blogStr, XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.newInstance().filed("上传失败");
        }
        return Result.newInstance().success("上传成功",blog);
    }

    @Override
    public Result queryFromES(String id) {
        Map<Integer, String> tagMap = getTagMap();
        GetRequest getRequest = new GetRequest(index,type,id);
        Blog blog = new Blog();
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                String sourceAsString = getResponse.getSourceAsString();
                blog = JSON.parseObject(sourceAsString, Blog.class);
                blog.setTagName(tagMap.getOrDefault(Integer.valueOf(blog.getTagId()),""));
                blog.setCreateTimeStr(DateUtils.format(blog.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            }
        } catch (IOException e) {
            logger.error("查询es失败：{}",e);
            return Result.newInstance().filed(e.getMessage());
        }
        return Result.newInstance().success("查询成功",blog);

    }

    @Override
    public void delFromES(String id) {
        DeleteRequest request = new DeleteRequest(index,type,id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            logger.info("删除成功：{}",JSON.toJSONString(deleteResponse));
        } catch (IOException e) {
            logger.error("删除失败：{}",e);
        }
    }

    @Override
    public Result queryPageFromES(BlogReqVO blogReqVO) {
        Map<Integer, String> tagMap = getTagMap();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(blogReqVO.getTitle())){
            boolQueryBuilder.must(QueryBuilders.termQuery("title", blogReqVO.getTitle()));
        }
        if (StringUtils.isNotEmpty(blogReqVO.getContent())){
            boolQueryBuilder.must(QueryBuilders.termQuery("content", blogReqVO.getContent()));
        }
        if (StringUtils.isNotEmpty(blogReqVO.getTagId())){
            boolQueryBuilder.must(QueryBuilders.termQuery("tagId", blogReqVO.getTagId()));
        }
        int pageSize = blogReqVO.getPageSize() <= 0 || blogReqVO.getPageSize() > 10 ? 10 : blogReqVO.getPageSize();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
                .from(pageSize * (blogReqVO.getCurrentPage()-1))
                .size(pageSize)
                .query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest(index).source(sourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getFailedShards() > 0) {
                return Result.newInstance().filed("查询失败");
            }
            List<Blog> blogList = new ArrayList<>();
            for (SearchHit hit : searchResponse.getHits()) {
                Blog blog = JSON.parseObject(hit.getSourceAsString(), Blog.class);
                blog.setTagName(tagMap.getOrDefault(Integer.valueOf(blog.getTagId()),""));
                blog.setCreateTimeStr(DateUtils.format(blog.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
                blogList.add(blog);
            }
            PageResult<Blog> blogPageResult = new PageResult<>(blogList, (int) searchResponse.getHits().getTotalHits().value, blogReqVO.getPageSize(), blogReqVO.getCurrentPage());
            return Result.newInstance().success("查询成功",blogPageResult);
        } catch (IOException e) {
            logger.error("查询es错误：{}",e);
            return Result.newInstance().filed("查询失败");
        }
    }

    /**
     * 创建index
     */
    @Override
    public Result createIndex(){
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(index);
            getIndexRequest.humanReadable(true);
            Boolean exists = restHighLevelClient.indices().exists(getIndexRequest,RequestOptions.DEFAULT);
            if (exists){
                logger.info("索引已存在");
                return Result.newInstance().filed("索引已存在");
            }

            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("properties")
                    .startObject("author").field("type", "text").field("index", true).endObject()
                    .startObject("content").field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer","ik_smart").endObject()
                    .startObject("id").field("type", "long").field("index", true).endObject()
                    .startObject("tagId").field("type", "text").field("index", true).endObject()
                    .startObject("title").field("type", "text").field("index", true).endObject()
                    .endObject().endObject();
            createIndexRequest.mapping(builder);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            if (acknowledged) {
                logger.info("创建索引成功");
                return Result.newInstance().success("创建索引成功",createIndexResponse);
            } else {
                logger.info("创建索引失败");
                return Result.newInstance().filed("创建索引失败");
            }
        } catch (Exception e) {
            logger.error("创建失败");
            return Result.newInstance().filed("创建索引失败");
        }
    }

    private Map<Integer, String> getTagMap(){
        List<Dic> list =  dicMapper.selectByTag();
        return list.stream().collect(Collectors.toMap(Dic::getId, Dic::getName));
    }
}
