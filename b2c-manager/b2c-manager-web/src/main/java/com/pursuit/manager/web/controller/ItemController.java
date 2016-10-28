package com.pursuit.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.pojo.TbItem;
import com.pursuit.service.ItemService;

@Controller
public class ItemController {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Value("${SEARCH_CONTENT}")
	private String SEARCH_CONTENT;
	@Value("${SEARCH_OPEATION}")
	private String SEARCH_OPEATION;
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public B2CResult createItem(TbItem item, String desc,String itemParams) {
		B2CResult result = itemService.createItem(item, desc,itemParams);
		//同步搜索引擎信息
		HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_CONTENT+SEARCH_OPEATION+"/"+result.getData());
		return result;
	}
}
