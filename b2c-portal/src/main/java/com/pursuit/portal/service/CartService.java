package com.pursuit.portal.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pursuit.common.B2CResult;
import com.pursuit.portal.pojo.CartItem;

public interface CartService {
	public B2CResult addCart(Long itemId, Integer num,HttpServletRequest request, HttpServletResponse response);
	public List<CartItem> getCartItems(HttpServletRequest request);
	public B2CResult updateCartItem(long itemId, Integer num, HttpServletRequest request,HttpServletResponse response);
	public B2CResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
	
}

