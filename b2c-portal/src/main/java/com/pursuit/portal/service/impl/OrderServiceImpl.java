package com.pursuit.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.HttpClientUtil;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.portal.pojo.OrderInfo;
import com.pursuit.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public String createOrder(OrderInfo orderInfo) {
		//把OrderInfo转换成json
		String json = JsonUtils.objectToJson(orderInfo);
		//提交订单数据
		String jsonResult = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, json);
		//转换成java对象
		B2CResult taotaoResult = B2CResult.format(jsonResult);
		//取订单号
		String orderId = taotaoResult.getData().toString();
		return orderId;
	}

}
