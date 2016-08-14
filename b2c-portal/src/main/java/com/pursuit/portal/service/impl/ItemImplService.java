package com.pursuit.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.FastJsonUtils;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.pojo.TbItemDesc;
import com.pursuit.pojo.TbItemParamItem;
import com.pursuit.portal.pojo.ItemInfo;
import com.pursuit.portal.service.ItemService;

@Service
public class ItemImplService implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASE}")
	private String REST_ITEM_BASE;
	@Value("${REST_ITEM_DESC}")
	private String REST_ITEM_DESC;
	@Value("${REST_ITEM_PARAM}")
	private String REST_ITEM_PARAM;
	
	@Override
	public ItemInfo getItemById(Long id) {
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_BASE+id);
		if(StringUtils.isNotBlank(json)){
			B2CResult result = B2CResult.formatToPojo(json, ItemInfo.class);
			return (ItemInfo) result.getData();
		}
		return null;
	}

	@Override
	public String getItemDescById(Long id) {
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_DESC+id);
		if(StringUtils.isNotBlank(json)){
			B2CResult result = B2CResult.formatToPojo(json, TbItemDesc.class);
			TbItemDesc desc = (TbItemDesc) result.getData();
			return desc.getItemDesc();
		}
		return null;
	}

	@Override
	public String getItemParamById(Long id) {
		String json = HttpClientUtil.doGet(REST_BASE_URL+REST_ITEM_PARAM+id);
		if(StringUtils.isNotBlank(json)){
			B2CResult result = B2CResult.formatToPojo(json, TbItemParamItem.class);
			TbItemParamItem itemParamItem = (TbItemParamItem) result.getData();
			if(null==itemParamItem){
				return null;
			}
			String paramData = itemParamItem.getParamData();
			List<Map> list = JsonUtils.jsonToList(paramData, Map.class);
			if(null!=list&&list.size()>0){
				StringBuffer sBuffer = new StringBuffer("<table cellpadding='0' cellspacing='1' width='100%' border='0' class='Ptable'><tbody>");
				for (Map map: list) {
					sBuffer.append("<tr><th class='tdTitle' colspan='2'>").append(map.get("group")).append("</th></tr>");
					ArrayList<Map> child = (ArrayList<Map>) map.get("params");
					if(null!=child&&child.size()>0){
						for(Map mapchild:child){
							sBuffer.append("<tr><td class='tdTitle'>").append(mapchild.get("k")).append("</td><td>").append(mapchild.get("v")).append("</td></tr>");
						}
					}
				}
				
				sBuffer.append("</tbody></table>");
				return sBuffer.toString();
			}
		}
		return null;
	}
public static void main(String[] args) {
	String str = "[{'group':'主体','params':[{'k':'品牌','v':'锤子'},{'k':'型号','v':'T1(SM705)'},{'k':'颜色','v':'黑色'},{'k':'上市年份','v':'2014年'}]},{'group':'网络','params':[{'k':'4G网络制式','v':'移动4G（TD-LTE）/联通4G（FDD-LTE）'},{'k':'3G网络制式','v':'移动3G(TD-SCDMA)/联通3G(WCDMA)'},{'k':'2G网络制式','v':'移动2G/联通2G(GSM)'}]},{'group':'存储','params':[{'k':'机身内存','v':'32GB ROM'},{'k':'储存卡类型','v':'2GB RAM'}]}]";
	List<Map>  list = FastJsonUtils.jsonToList(str, Map.class);
	List<Map>  list1 = JsonUtils.jsonToList(str, Map.class);
	
	System.out.println(list);
}
}
