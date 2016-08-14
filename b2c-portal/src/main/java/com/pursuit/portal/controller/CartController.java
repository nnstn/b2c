package com.pursuit.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.portal.pojo.CartItem;
import com.pursuit.portal.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		B2CResult result = cartService.addCart(itemId, num, request, response);
		return "cartSuccess";
	}
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, Model model) {
		List<CartItem> list = cartService.getCartItems(request);
		//把商品列表传递给jsp
		model.addAttribute("cartList", list);
		return "cart";
	}
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public B2CResult updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletResponse response, HttpServletRequest request) {
		B2CResult result = cartService.updateCartItem(itemId, num, request, response);
		return result;
	}
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,
			HttpServletResponse response, HttpServletRequest request) {
		B2CResult result = cartService.deleteCartItem(itemId, request, response);
		return "redirect:/cart/cart.html";
	}


}
