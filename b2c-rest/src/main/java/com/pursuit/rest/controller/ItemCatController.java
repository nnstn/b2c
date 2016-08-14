package com.pursuit.rest.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.rest.service.ItemCatService;

@Controller
@RequestMapping("/itemcat/")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="/all",produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	private String getItemCatResult(String callback){
		String result = itemCatService.getItemCatResult();
		if(null!=callback){
			result=callback+"("+result+");";
		}
		return result;
	}
	@RequestMapping("/sync")
	@ResponseBody
	public B2CResult syncItemCat(){
		return itemCatService.syncItemCat();
	}
}
