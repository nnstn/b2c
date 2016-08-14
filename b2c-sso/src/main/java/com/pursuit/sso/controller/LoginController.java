package com.pursuit.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.sso.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public B2CResult login(String username, String password, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			B2CResult result = loginService.login(username, password, request, response);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	@RequestMapping("/user/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		try {
			B2CResult result = loginService.getUserByToken(token);
			//支持jsonp调用
			if (StringUtils.isNotBlank(callback)) {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@RequestMapping(value="/user/logout/{token}", method=RequestMethod.POST)
	@ResponseBody
	public B2CResult login(@PathVariable String token) {
		try {
			B2CResult result = loginService.logout(token);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
