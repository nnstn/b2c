package com.pursuit.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.util.JsonUtils;
import com.pursuit.mapper.TbItemMapper;
import com.pursuit.pojo.TbItem;
import com.pursuit.pojo.TbItemExample;
import com.pursuit.pojo.TbItemExample.Criteria;
import com.pursuit.rest.component.JedisClient;
import com.pursuit.rest.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	Logger logger = Logger.getLogger(ItemServiceImpl.class);
	
	@Value("${REDIS_CONTENT}")
	private String REDIS_CONTENT;
	@Value("${REST_ITEM}")
	private String REST_ITEM;
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private JedisClient jedisClient;
	
	
	@Override
	public TbItem getItemById(Long itemId) {
		
		try {
			String json = jedisClient.get(REDIS_CONTENT+":"+REST_ITEM+":"+itemId);
			if(StringUtils.isNotBlank(json)){
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
		} catch (Exception e) {
			logger.error("读取缓存信息出错，请检测缓存配置  itemId："+itemId);
			e.printStackTrace();
		}
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(null!=list&&list.size()>0){
			try {
				jedisClient.set(REDIS_CONTENT+":"+REST_ITEM+":"+itemId, JsonUtils.objectToJson(list.get(0)));
				//设置key的过期时间
				jedisClient.expire(REDIS_CONTENT+":"+REST_ITEM+":"+itemId, ITEM_EXPIRE_SECOND);

			} catch (Exception e) {
				logger.error("存储缓存信息出错，请检测缓存配置  itemId："+itemId);
				e.printStackTrace();
			}
			
			return list.get(0);
		}
		return null;
	}

}
