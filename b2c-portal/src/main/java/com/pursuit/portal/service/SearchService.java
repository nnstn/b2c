package com.pursuit.portal.service;
import com.pursuit.portal.pojo.search.SearchResultProtal;

public interface SearchService {
	public SearchResultProtal search(String keyword,Integer page,Integer rows);
}
