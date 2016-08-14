package com.pursuit.service;

import java.util.List;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;

public interface ItemParamService {
	public EasyUIDataGridResult getItemParamList(int page,int rows);
	public B2CResult getItemParamByCid(Long cid);
	public B2CResult saveItemParam(Long cid,String paramData);
	public B2CResult deleteItemParam(Long cid);
	public B2CResult deleteItemParams(List<Long> values);
}
