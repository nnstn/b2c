package com.pursuit.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.portal.pojo.ItemInfo;
import com.pursuit.portal.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("{itemId}")
	private String getBaseItem(@PathVariable Long itemId,Model model){
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		return "item";
	}
	@RequestMapping(value="/desc/{itemId}" ,produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	private String getDescItem(@PathVariable Long itemId){
		String desc = itemService.getItemDescById(itemId);
		return desc;
	}
	@RequestMapping(value="/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	private String getParamItem(@PathVariable Long itemId){
		String html = itemService.getItemParamById(itemId);
		return html;
	}
	
	
	
}
