package com.pursuit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.EasyUIDataGridResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.pojo.TbContent;
import com.pursuit.service.ContentService;

@Controller
@RequestMapping(value="/content")
public class ContentController {
	@Value("${REST_BASE_URL}")
	private String  REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String  REST_CONTENT_URL;
	@Value("${REST_SYNC}")
	private String  REST_SYNC;
	
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	private String contentQueryList(@RequestParam(value="categoryId",defaultValue="0") Long categoryId,@RequestParam("page")int pageNum,@RequestParam("rows") int pageSize){
		EasyUIDataGridResult result = contentService.getContentByCategoryId(categoryId, pageNum, pageSize);
		return JsonUtils.objectToJson(result);
	}
	@RequestMapping("/save")
	@ResponseBody
	private B2CResult saveContent(TbContent content){
		try {
			B2CResult result = contentService.addContent(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/edit")
	@ResponseBody
	private B2CResult updateContent(TbContent content){
		try {
			B2CResult result = contentService.updateContent(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	private B2CResult deleteContents(String[] ids){
		try {
			B2CResult result = contentService.deleteContents(ids);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
