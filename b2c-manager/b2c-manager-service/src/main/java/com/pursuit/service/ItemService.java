package com.pursuit.service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.pojo.TbItem;

public interface ItemService {
	public TbItem getItemById(Long itemId);
 	public EasyUIDataGridResult getItemList(int page, int rows);
 	public B2CResult createItem(TbItem item, String desc,String itemParams);
}
