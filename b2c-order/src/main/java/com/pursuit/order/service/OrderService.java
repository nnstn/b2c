package com.pursuit.order.service;

import com.pursuit.common.B2CResult;
import com.pursuit.order.pojo.OrderInfo;

public interface OrderService {
	public B2CResult createOrder(OrderInfo orderInfo);
}
