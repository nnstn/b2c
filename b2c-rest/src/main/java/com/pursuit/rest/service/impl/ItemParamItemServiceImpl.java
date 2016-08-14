package com.pursuit.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.util.JsonUtils;
import com.pursuit.mapper.TbItemParamItemMapper;
import com.pursuit.pojo.TbItemParamItem;
import com.pursuit.pojo.TbItemParamItemExample;
import com.pursuit.pojo.TbItemParamItemExample.Criteria;
import com.pursuit.rest.component.JedisClient;
import com.pursuit.rest.service.ItemParamItemService;
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService{
	
	Logger logger = Logger.getLogger(ItemParamItemServiceImpl.class);
	
	@Value("${REDIS_CONTENT}")
	private String REDIS_CONTENT;
	@Value("${REST_ITEM_PARAM}")
	private String REST_ITEM_PARAM;
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	@Autowired
	private JedisClient jedisClient;
	
	
	@Override
	public TbItemParamItem getItemById(Long itemId) {
		
		try {
			String json = jedisClient.get(REDIS_CONTENT+":"+REST_ITEM_PARAM+":"+itemId);
			if(StringUtils.isNotBlank(json)){
				return JsonUtils.jsonToPojo(json, TbItemParamItem.class);
			}
		} catch (Exception e) {
			logger.error("读取缓存信息出错，请检测缓存配置  itemId："+itemId);
			e.printStackTrace();
		}
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(null!=list&&list.size()>0){
			try {
				jedisClient.set(REDIS_CONTENT+":"+REST_ITEM_PARAM+":"+itemId, JsonUtils.objectToJson(list.get(0)));
				//设置key的过期时间
				jedisClient.expire(REDIS_CONTENT+":"+REST_ITEM_PARAM+":"+itemId, ITEM_EXPIRE_SECOND);

			} catch (Exception e) {
				logger.error("存储缓存信息出错，请检测缓存配置  itemId："+itemId);
				e.printStackTrace();
			}
			
			return list.get(0);
		}
		return null;
	}

}
