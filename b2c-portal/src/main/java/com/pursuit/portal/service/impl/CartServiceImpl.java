package com.pursuit.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pursuit.common.B2CResult;
import com.pursuit.common.util.CookieUtils;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.pojo.TbItem;
import com.pursuit.portal.pojo.CartItem;
import com.pursuit.portal.service.CartService;
import com.pursuit.portal.service.ItemService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private ItemService itemService;
	@Value("${COOKIE_EXPIRE}")
	private Integer COOKIE_EXPIRE;

	@Override
	public B2CResult addCart(Long itemId, Integer num,HttpServletRequest request, HttpServletResponse response) {
		// 1、接收商品id
		// 2、从cookie中购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 3、从商品列表中查询列表是否存在此商品
		boolean haveFlg = false;
		for (CartItem cartItem : itemList) {
			//如果商品存在数量相加
			// 4、如果存在商品的数量加上参数中的商品数量
			if (cartItem.getId() == itemId) {
				cartItem.setNum((cartItem.getNum()==null?0:cartItem.getNum()) + num);
				haveFlg = true;
				break;
			}
		}
		// 5、如果不存在，调用rest服务，根据商品id获得商品数据。
		if (!haveFlg) {
			TbItem item = itemService.getItemById(itemId);
			//转换成CartItem
			CartItem cartItem = new CartItem();
			cartItem.setId(itemId);
			cartItem.setNum(num);
			cartItem.setPrice(item.getPrice());
			cartItem.setTitle(item.getTitle());
			if (StringUtils.isNotBlank(item.getImage())) {
				String image = item.getImage();
				String[] strings = image.split(",");
				cartItem.setImage(strings[0]);
			}
			//添加到购物车商品列表
			// 6、把商品数据添加到列表中
			itemList.add(cartItem);
		}
		// 7、把购物车商品列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		// 8、返回B2CResult
		return B2CResult.ok();
	}
	
	/**
	 * 取购物车商品列表
	 * <p>Title: getCartItemList</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		try {
			//从cookie中取商品列表
			String json = CookieUtils.getCookieValue(request, "TT_CART", true);
			//把json转换成java对象
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			
			return list==null?new ArrayList<CartItem>():list;
			
		} catch (Exception e) {
			return new ArrayList<CartItem>();
		}
		
	}
	@Override
	public List<CartItem> getCartItems(HttpServletRequest request){
		List<CartItem> list = getCartItemList(request);
		return list;
	}

	@Override
	public B2CResult updateCartItem(long itemId, Integer num, HttpServletRequest request,HttpServletResponse response) {
		// 从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		//根据商品id查询商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				//更新数量
				cartItem.setNum(num);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		return B2CResult.ok();
	}
	@Override
	public B2CResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 1、接收商品id
		// 2、从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 3、找到对应id的商品
		for (CartItem cartItem : itemList) {
			if (cartItem.getId() == itemId) {
				// 4、删除商品。
				itemList.remove(cartItem);
				break;
			}
		}
		// 5、再重新把商品列表写入cookie。
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), COOKIE_EXPIRE, true);
		// 6、返回成功
		return B2CResult.ok();
	}

}
