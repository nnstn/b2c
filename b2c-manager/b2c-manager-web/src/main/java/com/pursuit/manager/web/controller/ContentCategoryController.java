package com.pursuit.manager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUITreeNode;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.service.ContentCategoryService;

@Controller
@RequestMapping(value="/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	private String getContentCategory(@RequestParam(value="id",defaultValue="0") Long parentId){
		List<EasyUITreeNode> result = contentCategoryService.getContentCategory(parentId);
		String rst = JsonUtils.objectToJson(result);
		return rst;
	}
	@RequestMapping("/create")
	@ResponseBody
	private B2CResult createContentCategory(Long parentId,String name){
		B2CResult result = contentCategoryService.createContentCategory(parentId, name);
		return result;
	}
	@RequestMapping("/update")
	@ResponseBody 
	private B2CResult updateContentCategory(Long id,String name){
		B2CResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}
	@RequestMapping("/delete")
	@ResponseBody 
	private B2CResult deleteContentCategory(Long parentId,Long id){
		B2CResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
}
