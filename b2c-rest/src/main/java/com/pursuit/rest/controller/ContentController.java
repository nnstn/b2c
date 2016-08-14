package com.pursuit.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.pojo.TbContent;
import com.pursuit.rest.service.ContentService;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/{cid}")
	@ResponseBody
	public B2CResult getContentList(@PathVariable Long cid) {
		try {
			List<TbContent> list = contentService.getContentList(cid);
			return B2CResult.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/content/sync/{cid}")
	@ResponseBody
	public B2CResult syncContent(@PathVariable Long cid) {
		try {
			return contentService.syncContent(cid);
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
}
