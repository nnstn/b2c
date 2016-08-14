package com.pursuit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.mapper.TbContentMapper;
import com.pursuit.pojo.TbContent;
import com.pursuit.pojo.TbContentExample;
import com.pursuit.pojo.TbContentExample.Criteria;
import com.pursuit.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	@Value("${REST_BASE_URL}")
	private String  REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String  REST_CONTENT_URL;
	@Value("${REST_SYNC}")
	private String  REST_SYNC;
	
	@Autowired
	private TbContentMapper contentMapper; 
	
	@Override
	public EasyUIDataGridResult getContentByCategoryId(Long categoryId,int pageNum,int pageSize) {
		//1.拼装请求执行查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		//2.分页信息
		PageHelper.startPage(pageNum, pageSize);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		PageInfo pageInfo = new PageInfo(list);
		long total = pageInfo.getTotal();
		//3.返回结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(total);
		return result;
	}

	@Override
	public B2CResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//同步缓存
		syncContent(content.getCategoryId());
		return B2CResult.ok();
	}

	@Override
	public B2CResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		//同步缓存
		syncContent(content.getCategoryId());
		return  B2CResult.ok();
	}

	@Override
	public B2CResult deleteContent(Long id) {
		contentMapper.deleteByPrimaryKey(id);
		//同步缓存
		syncContent(id);
		return B2CResult.ok();
	}
	public B2CResult deleteContents(String[] ids){
		List<Long> values =new ArrayList<Long>();
		for (String id : ids) {
			values.add(Long.parseLong(id));
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(values);
		contentMapper.deleteByExample(example);
		//同步缓存
		for (int i = 0; i < ids.length; i++) {
			syncContent(Long.getLong(ids[i]));
		}
		return B2CResult.ok();
	}
	private void syncContent(Long cid){
		HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+REST_SYNC+"/"+cid);
	}
}
