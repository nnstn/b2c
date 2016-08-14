package com.pursuit.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pursuit.pojo.TbUser;
import com.pursuit.portal.pojo.CartItem;
import com.pursuit.portal.pojo.OrderInfo;
import com.pursuit.portal.service.CartService;
import com.pursuit.portal.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(Model model, HttpServletRequest request) {
		//取购物车商品列表
		List<CartItem> list = cartService.getCartItems(request);
		model.addAttribute("cartList", list);
		return "order-cart";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {
		//取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		//补全orderIn的属性
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//调用服务
		String orderId = orderService.createOrder(orderInfo);
		//把订单号传递个页面
		model.addAttribute("orderId", orderId);
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		//返回逻辑视图
		return "success";
	}

}
