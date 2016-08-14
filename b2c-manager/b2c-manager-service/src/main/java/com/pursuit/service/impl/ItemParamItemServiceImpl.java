package com.pursuit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.mapper.TbItemParamItemMapper;
import com.pursuit.pojo.TbItemParamItem;
import com.pursuit.pojo.TbItemParamItemExample;
import com.pursuit.pojo.TbItemParamItemExample.Criteria;
import com.pursuit.service.ItemParamItemService;
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	 
	@Override
	public B2CResult getItemParamItemById(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(null!=list && list.size()>0){
			return B2CResult.ok(list.get(0));
		}
		return B2CResult.ok();
	}

}
