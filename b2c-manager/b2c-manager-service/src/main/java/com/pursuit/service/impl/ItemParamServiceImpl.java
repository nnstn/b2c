package com.pursuit.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.mapper.TbItemCatMapper;
import com.pursuit.mapper.TbItemParamMapper;
import com.pursuit.pojo.TbItemCat;
import com.pursuit.pojo.TbItemParam;
import com.pursuit.pojo.TbItemParamExample;
import com.pursuit.pojo.TbItemParamExample.Criteria;
import com.pursuit.service.ItemParamService;
@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {
		//分页处理
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		for (int i = 0; i < list.size(); i++) {
			TbItemParam itemParam = list.get(i);
			Long itemCatId = itemParam.getItemCatId();
			TbItemCat itemCat = getTbItemCatbyCid(itemCatId);
			itemParam.setItemCatName(itemCat.getName());
		}
		//取分页信息
		PageInfo pageInfo = new PageInfo(list);
		//返回处理结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}
	private TbItemCat getTbItemCatbyCid(Long cid){
		TbItemCat itemCat = tbItemCatMapper.selectByPrimaryKey(cid);
		return itemCat;
	}
	@Override
	public B2CResult getItemParamByCid(Long cid){
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(null!=list && list.size()>0){
			return B2CResult.ok(list.get(0));
		}
		return B2CResult.ok();
	}
	@Override
	public B2CResult saveItemParam(Long cid,String paramData){
		//1.构建tbItemParam对象
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		//2.保存操作
		tbItemParamMapper.insert(tbItemParam);
		return B2CResult.ok();
	}
	

	@Override
	public B2CResult deleteItemParam(Long cid) {
		tbItemParamMapper.deleteByPrimaryKey(cid);
		return B2CResult.ok();
	}
	@Override
	public B2CResult deleteItemParams(List<Long> values) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(values);
		tbItemParamMapper.deleteByExample(example);
		return B2CResult.ok();
	}
}
