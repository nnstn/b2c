package com.pursuit.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.CookieUtils;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.pojo.TbUser;
import com.pursuit.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;
	

	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			//从cookie中取token
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			//判断token是否有值
			if (StringUtils.isBlank(token)) {
				return null;
			}
			//调用sso的服务查询用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN_SERVICE + token);
			//把json转换成java对象
			B2CResult result = B2CResult.format(json);
			if (result.getStatus() != 200) {
				return null;
			}
			//取用户对象
			result = B2CResult.formatToPojo(json, TbUser.class);
			TbUser user = (TbUser) result.getData();
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
