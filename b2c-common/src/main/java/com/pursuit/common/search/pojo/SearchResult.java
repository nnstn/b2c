package com.pursuit.common.search.pojo;

import java.util.List;

public class SearchResult {
	private List<SearchItem> items;
	private long recordCount;
	private int pageCount;
	private int curPage;
	public List<SearchItem> getItems() {
		return items;
	}
	public void setItems(List<SearchItem> items) {
		this.items = items;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
}
