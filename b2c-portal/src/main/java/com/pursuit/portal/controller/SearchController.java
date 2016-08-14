package com.pursuit.portal.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pursuit.portal.pojo.search.SearchResultProtal;
import com.pursuit.portal.service.SearchService;

@Controller
public class SearchController {
	@Autowired
	private SearchService searchService;
	@RequestMapping("/search")
	public String search(@RequestParam("q")String keyword, 
			@RequestParam(defaultValue="1")Integer page, 
			@RequestParam(defaultValue="60")Integer rows, Model model) {
		//get乱码处理
		try {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			keyword = "";
			e.printStackTrace();
		}
		SearchResultProtal searchResult = searchService.search(keyword, page, rows);
		//参数传递 给页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItems());
		model.addAttribute("page", searchResult.getCurPage());
		
		//返回逻辑视图
		return "search";
	}

	
}
