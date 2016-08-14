package com.pursuit.search.service;
import com.pursuit.common.B2CResult;

public interface ItemService {
	public B2CResult getItemList()throws Exception ;
	public B2CResult update(Long id) throws Exception;
}
