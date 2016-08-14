package com.pursuit.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.pojo.TbItemDesc;
import com.pursuit.rest.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	private B2CResult getItemDescById(@PathVariable Long itemId){
		 try {
			TbItemDesc itemDesc = itemDescService.getItemDescById(itemId);
			 return B2CResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
