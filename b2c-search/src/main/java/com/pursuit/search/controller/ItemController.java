package com.pursuit.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.search.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/importall")
	@ResponseBody
	public B2CResult importAll(){
		try {
			B2CResult result = itemService.getItemList();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/update/{id}") 
	@ResponseBody
	public B2CResult update(@PathVariable Long id){
		try {
			B2CResult result = itemService.update(id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
