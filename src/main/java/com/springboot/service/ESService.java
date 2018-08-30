package com.springboot.service;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ESService {

    public CreateIndexResponse createIndex(String indexName);

    public IndexResponse write(String indexName);

    public SearchResponse search(String indexName, Integer from, Integer size);

    public DeleteIndexResponse deleteIndex(String indexName);


}
