package com.pursuit.service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.pojo.TbContent;

public interface ContentService {
	public EasyUIDataGridResult getContentByCategoryId(Long categoryId,int pageNum,int pageSize); 
	public B2CResult addContent(TbContent content);
	public B2CResult updateContent(TbContent content);
	public B2CResult deleteContent(Long id);
	public B2CResult deleteContents(String[] ids);
}
