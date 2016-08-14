package com.pursuit.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pursuit.common.B2CResult;

public interface LoginService {
	public B2CResult login(String username, String password, HttpServletRequest request,HttpServletResponse response);
	public B2CResult getUserByToken(String token);
	public B2CResult logout(String token);
}
