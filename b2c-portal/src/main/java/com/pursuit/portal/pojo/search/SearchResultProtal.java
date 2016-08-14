package com.pursuit.portal.pojo.search;

import java.util.List;

public class SearchResultProtal {
	private List<SearchItemProtal> items;
	private long recordCount;
	private int pageCount;
	private int curPage;
	public List<SearchItemProtal> getItems() {
		return items;
	}
	public void setItems(List<SearchItemProtal> items) {
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
