package com.pursuit.service;

import java.util.List;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUITreeNode;

public interface ContentCategoryService {
	public List<EasyUITreeNode> getContentCategory(Long parentId);
	public B2CResult createContentCategory(Long parentId,String nodeName);
	public B2CResult updateContentCategory(Long id,String nodeName);
	public B2CResult deleteContentCategory(Long id);
}
