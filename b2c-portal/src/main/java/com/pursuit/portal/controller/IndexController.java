package com.pursuit.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pursuit.portal.service.ContentService;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/index")
	private String indexPage(Model model){
		try {
			String ad1 = contentService.getAd1List();
			model.addAttribute("ad1", ad1);
		} catch (Exception e) {
			model.addAttribute("error", "广告信息获取失败");
			//return "error";
		}
		return "index";
	}
	
}
