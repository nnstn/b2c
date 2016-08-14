package com.pursuit.sso.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	/**
	 * 展示注册页面
	 */
	@RequestMapping("/page/login")
	public String showLogin(String redirect,Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}
	/**
	 * 展示登录页面
	 */
	@RequestMapping("/page/register")
	public String showRegister() {
		
		return "register";
	}
}
