package com.pursuit.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/list")
	@ResponseBody
	private  EasyUIDataGridResult getItemCatList(Integer page, Integer rows){
		EasyUIDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}
	@RequestMapping("/query/itemcatid/{itemcatid}")
	@ResponseBody
	private  B2CResult getItemParamByCid(@PathVariable(value="itemcatid") Long itemcatid){
		B2CResult result = itemParamService.getItemParamByCid(itemcatid);
		return result;
	}
	@RequestMapping("/save/{cid}")
	@ResponseBody
	private B2CResult saveItemParam(@PathVariable Long cid,String paramData){
		B2CResult result = itemParamService.saveItemParam(cid,paramData);
		return result;
	}
	@RequestMapping("/delete")
	@ResponseBody
	private B2CResult deleteItemParam(String ids) {
		String[] strs = ids.split(",");
		List<Long> values = new ArrayList<Long>();
		for (int i = 0; i < strs.length; i++) {
			Long num = Long.parseLong(strs[i]);
			values.add(num);
		}
		B2CResult result = itemParamService.deleteItemParams(values);
		return result;
	}
	
}
