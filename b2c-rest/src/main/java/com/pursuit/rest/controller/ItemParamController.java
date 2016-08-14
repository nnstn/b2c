package com.pursuit.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.pojo.TbItemParamItem;
import com.pursuit.rest.service.ItemParamItemService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	private B2CResult getItemDescById(@PathVariable Long itemId){
		 try {
			TbItemParamItem itemParam = itemParamItemService.getItemById(itemId);
			 return B2CResult.ok(itemParam);
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
