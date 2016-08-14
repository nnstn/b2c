package com.pursuit.rest.service;

import java.util.List;

import com.pursuit.common.B2CResult;
import com.pursuit.pojo.TbContent;

public interface ContentService {
	public List<TbContent> getContentList(Long cid);
	public B2CResult syncContent(Long cid);
}
