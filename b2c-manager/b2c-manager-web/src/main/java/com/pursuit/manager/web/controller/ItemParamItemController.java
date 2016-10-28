package com.pursuit.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.service.ItemParamItemService;

@Controller
@RequestMapping("/rest/item/param/item")
public class ItemParamItemController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/query/{itemId}")
	@ResponseBody
	private B2CResult getItemParamItemById(@PathVariable Long itemId){
		B2CResult result = itemParamItemService.getItemParamItemById(itemId);
		return result;
	} 
}
