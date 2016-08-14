package com.pursuit.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.util.JsonUtils;
import com.pursuit.mapper.TbItemDescMapper;
import com.pursuit.pojo.TbItemDesc;
import com.pursuit.pojo.TbItemDescExample;
import com.pursuit.pojo.TbItemDescExample.Criteria;
import com.pursuit.rest.component.JedisClient;
import com.pursuit.rest.service.ItemDescService;
@Service
public class ItemDescServiceImpl implements ItemDescService {
	Logger logger = Logger.getLogger(ItemDescServiceImpl.class);
	@Value("${REDIS_CONTENT}")
	private String REDIS_CONTENT;
	@Value("${REST_ITEM_DESC}")
	private String REST_ITEM_DESC;
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;
	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		try {
			String json = jedisClient.get(REDIS_CONTENT+":"+REST_ITEM_DESC+":"+itemId);
			if(StringUtils.isNotBlank(json)){
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		} catch (Exception e) {
			logger.error("读取缓存信息出错，请检测缓存配置  itemId："+itemId);
			e.printStackTrace();
		}
		TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = itemDescMapper.selectByExampleWithBLOBs(example);
		if(null!=list&&list.size()>0){
			try {
				jedisClient.set(REDIS_CONTENT+":"+REST_ITEM_DESC+":"+itemId, JsonUtils.objectToJson(list.get(0)));
				//设置key的过期时间
				jedisClient.expire(REDIS_CONTENT+":"+REST_ITEM_DESC+":"+itemId, ITEM_EXPIRE_SECOND);

			} catch (Exception e) {
				logger.error("存储缓存信息出错，请检测缓存配置  itemId："+itemId);
				e.printStackTrace();
			}
			return list.get(0);
		}
		return null;
	}

}
