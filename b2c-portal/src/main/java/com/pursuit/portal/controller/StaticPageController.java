package com.pursuit.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.portal.service.StaticPageService;

@Controller
public class StaticPageController {

	@Autowired
	private StaticPageService staticPageService;
	
	@RequestMapping("/gen/item/{itemId}")
	@ResponseBody
	public B2CResult genItemPage(@PathVariable Long itemId) {
		try {
			B2CResult result = staticPageService.genItemHtml(itemId);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}