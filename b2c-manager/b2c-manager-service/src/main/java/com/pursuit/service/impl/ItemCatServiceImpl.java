package com.pursuit.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pursuit.common.EasyUITreeNode;
import com.pursuit.mapper.TbItemCatMapper;
import com.pursuit.pojo.TbItemCat;
import com.pursuit.pojo.TbItemCatExample;
import com.pursuit.pojo.TbItemCatExample.Criteria;
import com.pursuit.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Override
	public List<EasyUITreeNode> getItemCatList(Long parentId) {
		// 根据parentId查询分类列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
		List<EasyUITreeNode> treeNodes  = new ArrayList<EasyUITreeNode>();
		if(null!=itemCats&&itemCats.size()>0){
			for (TbItemCat itemCat:itemCats) {
				EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
				easyUITreeNode.setId(itemCat.getId());
				easyUITreeNode.setText(itemCat.getName());
				easyUITreeNode.setState(itemCat.getIsParent()?"closed":"open");
				treeNodes.add(easyUITreeNode);
			}
		}
		return treeNodes;
	}
}
