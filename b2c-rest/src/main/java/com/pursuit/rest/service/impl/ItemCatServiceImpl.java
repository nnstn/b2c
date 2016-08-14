package com.pursuit.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.mapper.TbItemCatMapper;
import com.pursuit.pojo.TbItemCat;
import com.pursuit.pojo.TbItemCatExample;
import com.pursuit.pojo.TbItemCatExample.Criteria;
import com.pursuit.rest.component.JedisClient;
import com.pursuit.rest.pojo.CatNode;
import com.pursuit.rest.pojo.CatResult;
import com.pursuit.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REST_ITEM_CAT}")
	private String REST_ITEM_CAT;
	
	@Override
	public String getItemCatResult() {
		try {
			//读取缓存信息
			String itemcat = jedisClient.get(REST_ITEM_CAT);
			if(null!=itemcat){
				return itemcat;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//调用递归方法查询商品分类列表
		List catList = getCatList(0L);
		//返回结果
		CatResult result = new CatResult();
		result.setData(catList);
		String json = JsonUtils.objectToJson(result);
		try {
			//存入缓存信息
			jedisClient.set(REST_ITEM_CAT,json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@SuppressWarnings("rawtypes")
	private List getCatList(Long parentid){
		//1.查询商品类别
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		@SuppressWarnings("unchecked")
		List<Object> resultList = new ArrayList();
		int index=0;
		if(null!=list&&list.size()>0){
			for (TbItemCat itemCat:list) {
				if(index<14){
					if(itemCat.getIsParent()){
						CatNode node = new CatNode();
						if(itemCat.getParentId()==0){
							node.setUrl("/products/"+itemCat.getId()+".html");
							node.setName("<a href='/products/"+itemCat.getId()+".html'>"+itemCat.getName()+"</a>");
							node.setItem(getCatList(itemCat.getId()));
							index++;
						}else{
							node.setUrl("/products/"+itemCat.getId()+".html");
							node.setName(itemCat.getName());
							node.setItem(getCatList(itemCat.getId()));
						}
						resultList.add(node);
					}else{
						resultList.add("/products/"+itemCat.getId()+".html|"+itemCat.getName());
					}
				}else{
					System.out.println("数据过多--丢弃");
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * 清除类别缓存
	 * @return
	 */
	@Override
	public B2CResult syncItemCat(){
		jedisClient.del(REST_ITEM_CAT);
		return B2CResult.ok();
	}
}
