package com.pursuit.sso.service;

import com.pursuit.common.B2CResult;
import com.pursuit.pojo.TbUser;

public interface RegisterService {
	
	B2CResult checkData(String param, int type);
	B2CResult register(TbUser user);
}
