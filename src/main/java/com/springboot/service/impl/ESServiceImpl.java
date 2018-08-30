package com.springboot.service.impl;


import com.springboot.mybatis.dao.SUserDao;
import com.springboot.mybatis.dao.TsmRelationDao;
import com.springboot.mybatis.model.SUserDomain;
import com.springboot.service.ESService;
import com.springboot.transform.ESTransformer;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;


import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

@Service
public class ESServiceImpl implements ESService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    SUserDao sUserDao;
    @Autowired
    TsmRelationDao tsmRelationDao;


    public CreateIndexResponse createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        CreateIndexResponse response = null;
        try {
            response = restHighLevelClient.indices().create(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public IndexResponse write(String indexName) {
        SUserDomain userDomain = sUserDao.findById(940);
        IndexRequest request = new IndexRequest(indexName, indexName, String.valueOf(userDomain.getId()));
        Map<String, Object> jsonMap = ESTransformer.transform2Map(userDomain);
        request.source(jsonMap, XContentType.JSON);
        try {
            return restHighLevelClient.index(request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new IndexResponse();

    }

    public DeleteIndexResponse deleteIndex(String indexName) {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        DeleteIndexResponse response = null;
        try {
            response = restHighLevelClient.indices().delete(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public BulkResponse bulkWrite(String indexName) {
        BulkRequest bulkRequest = new BulkRequest();
        BulkResponse bulkResponse = null;
//        sUserDao.findAll().forEach(user -> {
//            IndexRequest indexRequest =
//                    new IndexRequest(indexName,indexName,String.valueOf(user.getId()));
//            Map<String,Object> jsonMap = ESTransformer.transform2Map(user);
//            bulkRequest.add(indexRequest.source(jsonMap,XContentType.JSON));
//        });
        tsmRelationDao.findAll().forEach(user -> {
            IndexRequest indexRequest =
                    new IndexRequest(indexName, indexName);
            Map<String, Object> jsonMap = ESTransformer.transform2Map(user);
            bulkRequest.add(indexRequest.source(jsonMap, XContentType.JSON));
        });

        try {
            bulkResponse = restHighLevelClient.bulk(bulkRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bulkResponse;
    }


    public SearchResponse search(String indexName, Integer from, Integer size) {
        if (!isIndexExist(indexName)) {
            return null;
        }
        SearchResponse searchResponse = null;
        SearchRequest searchRequest = new SearchRequest().indices(indexName);
        FieldSortBuilder fieldSortBuilder = new FieldSortBuilder("id").order(SortOrder.ASC);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(termsQuery("ouName", "广州天河胶管制品有限公司"));
        searchSourceBuilder.from(from == null ? 0 : from);
        searchSourceBuilder.size(size == null ? 10 : size);
        searchSourceBuilder.sort(fieldSortBuilder);

        searchRequest.source(searchSourceBuilder);
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }


    public SearchResponse searchScroll(String indexName, Integer size) {
        SearchResponse searchResponse = null;
        SearchRequest request = new SearchRequest(indexName);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().size(size);
        sourceBuilder.query(matchAllQuery());
        request.source(sourceBuilder);
        request.scroll(TimeValue.timeValueMinutes(1));

        try {
            searchResponse = restHighLevelClient.search(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }


    public SearchResponse searchScrollNext(String scrollId) {
        SearchResponse searchResponse = null;
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(new Scroll(TimeValue.timeValueMinutes(1)));
        try {
            searchResponse = restHighLevelClient.searchScroll(scrollRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }


    public boolean isIndexExist(String indexName) {


        try {
            return restHighLevelClient.indices().exists(new GetIndexRequest().indices(indexName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
