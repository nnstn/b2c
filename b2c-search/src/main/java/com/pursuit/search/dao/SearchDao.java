package com.pursuit.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.pursuit.common.search.pojo.SearchResult;


public interface SearchDao {
	public SearchResult search(SolrQuery query) throws Exception;
}
