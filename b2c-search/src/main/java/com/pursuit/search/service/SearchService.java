package com.pursuit.search.service;

import com.pursuit.common.search.pojo.SearchResult;

public interface SearchService {
	public SearchResult search(String queryString, int page, int rows) throws Exception ;
}
