package com.pursuit.search.mapper;

import java.util.List;

import com.pursuit.common.search.pojo.SearchItem;

public interface ItemMapper {
	public List<SearchItem> getItemList();
	public SearchItem getItemById(Long id);
}
