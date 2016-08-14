package com.pursuit.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.pursuit.common.B2CResult;
import com.pursuit.mapper.TbUserMapper;
import com.pursuit.pojo.TbUser;
import com.pursuit.pojo.TbUserExample;
import com.pursuit.pojo.TbUserExample.Criteria;
import com.pursuit.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper;

	@Override
	public B2CResult checkData(String param, int type) {
		//根据数据类型检查数据
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1、2、3分别代表username、phone、email
		if (1 == type) {
			criteria.andUsernameEqualTo(param);
		} else if ( 2 == type) {
			criteria.andPhoneEqualTo(param);
		} else if ( 3 == type ) {
			criteria.andEmailEqualTo(param);
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//判断查询结果是否为空
		if (list == null || list.isEmpty()) {
			return B2CResult.ok(true);
		}
		return B2CResult.ok(false);
	}
	/**
	 * 用户注册
	 * <p>Title: register</p>
	 * <p>Description: </p>
	 * @param user
	 * @return
	 * @see com.taotao.sso.service.RegisterService#register(com.taotao.pojo.TbUser)
	 */
	@Override
	public B2CResult register(TbUser user) {
		// 校验数据
		//校验用户名、密码不能为空
		if (StringUtils.isBlank(user.getUsername())
				|| StringUtils.isBlank(user.getPassword())) {
			return B2CResult.build(400, "用户名或密码不能为空");
		}
		//校验数据是否重复
		//校验用户名
		B2CResult result = checkData(user.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return B2CResult.build(400, "用户名重复");
		}
		//校验手机号
		if (user.getPhone() != null) {
			result = checkData(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return B2CResult.build(400, "手机号重复");
			}
		}
		//校验手机号
		if (user.getEmail() != null) {
			result = checkData(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return B2CResult.build(400, "邮箱重复");
			}
		}
		//插入数据
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		userMapper.insert(user);
		
		return B2CResult.ok();
	}


}

