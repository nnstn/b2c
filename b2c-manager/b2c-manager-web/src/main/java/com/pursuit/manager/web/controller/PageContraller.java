package com.pursuit.manager.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageContraller {
	
	@RequestMapping("/")
	private String showIndex(){
		return "index";
	}
	@RequestMapping("/{page}")
	private String page(@PathVariable String page){
		return page;
	}
}
