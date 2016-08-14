package com.pursuit.service;
import java.util.List;
import com.pursuit.common.EasyUITreeNode;

public interface ItemCatService {
	public List<EasyUITreeNode> getItemCatList(Long parentId);
}
