package com.pursuit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.ExceptionUtil;
import com.pursuit.order.pojo.OrderInfo;
import com.pursuit.order.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	@ResponseBody
	public B2CResult createOrder(@RequestBody OrderInfo orderInfo) {
		try {
			B2CResult result = orderService.createOrder(orderInfo);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return B2CResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
