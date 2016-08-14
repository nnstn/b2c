package com.pursuit.portal.service;

import com.pursuit.portal.pojo.ItemInfo;

public interface ItemService{
	public ItemInfo getItemById(Long id);
	public String getItemDescById(Long id);
	public String getItemParamById(Long id);
	
}
