package com.pursuit.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.mapper.TbContentMapper;
import com.pursuit.pojo.TbContent;
import com.pursuit.pojo.TbContentExample;
import com.pursuit.pojo.TbContentExample.Criteria;
import com.pursuit.rest.component.JedisClient;
import com.pursuit.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	Logger logger = Logger.getLogger(ContentServiceImpl.class);
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REST_CONTENT_FLAG}")
	private String rest_content_flag;
	
	@Override
	public List<TbContent> getContentList(Long cid) {
		//添加缓存
		//查询数据库之前先查询缓存，如果有直接返回
		try {
			//从redis中取缓存数据
			String hget = jedisClient.hget(rest_content_flag, cid+"");
			StringUtils.isBlank(hget);
			if(null!=hget){
				List<TbContent> jsonToList = JsonUtils.jsonToList(hget, TbContent.class);
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据cid查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//返回结果之前，向缓存中添加数据
		try {
			//为了规范key可以使用hash
			//定义一个保存内容的key，hash中每个项就是cid
			//value是list，需要把list转换成json数据。
			String json = JsonUtils.objectToJson(list);
			jedisClient.hset(rest_content_flag, cid+"",json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public B2CResult syncContent(Long cid){
		jedisClient.hdel(rest_content_flag, cid+"");
		return B2CResult.ok();
	}

}
