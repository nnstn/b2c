package com.pursuit.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.search.pojo.SearchResult;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.portal.pojo.search.SearchResultProtal;
import com.pursuit.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Value("${SEARCH_URL}")
	private String SEARCH_URL;
	@Override
	public SearchResultProtal search(String keyword, Integer page, Integer rows) {
		//调用服务查询商品列表
		Map<String, String> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("page", page + "");
		param.put("rows", rows + "");
		
		//调用服务
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_URL, param);
		//转换成java对象
		B2CResult result = B2CResult.formatToPojo(json, SearchResultProtal.class);
		//取返回的结果
		SearchResultProtal searchResult = (SearchResultProtal) result.getData();
		return searchResult;
	}


}
