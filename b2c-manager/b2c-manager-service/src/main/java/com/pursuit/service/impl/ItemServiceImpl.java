package com.pursuit.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.common.util.IDUtils;
import com.pursuit.mapper.TbItemDescMapper;
import com.pursuit.mapper.TbItemMapper;
import com.pursuit.mapper.TbItemParamItemMapper;
import com.pursuit.pojo.TbItem;
import com.pursuit.pojo.TbItemDesc;
import com.pursuit.pojo.TbItemExample;
import com.pursuit.pojo.TbItemExample.Criteria;
import com.pursuit.pojo.TbItemParamItem;
import com.pursuit.service.ItemService;

/**
 * 商品查询Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年11月11日下午4:28:55
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(Long itemId) {
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		//创建查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		//判断list中是否为空
		TbItem item = null;
		if (list != null && list.size() > 0) {
			item = list.get(0);
		}
		return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo pageInfo = new PageInfo(list);
		//返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	public B2CResult saveItem(TbItem tbItem){
		itemMapper.insert(tbItem);
		return B2CResult.ok();
	}
	@Override
	public B2CResult createItem(TbItem item, String desc,String itemParams) {
		// 生成商品id
		long itemId = IDUtils.genItemId();
		// 补全TbItem属性
		item.setId(itemId);
		// '商品状态，1-正常，2-下架，3-删除'
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品表
		itemMapper.insert(item);
		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 插入商品描述数据
		itemDescMapper.insert(itemDesc);
		
		//商品规格参数表
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//插入商品规格参数表
		itemParamItemMapper.insert(itemParamItem);
		return B2CResult.ok(itemId);
	}

}
