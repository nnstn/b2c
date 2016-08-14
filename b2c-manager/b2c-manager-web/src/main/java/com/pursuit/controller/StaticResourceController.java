package com.pursuit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pursuit.common.PictureResult;
import com.pursuit.common.util.JsonUtils;
import com.pursuit.service.StaticResourceService;

@Controller
public class StaticResourceController {
	@Autowired
	private StaticResourceService staticResourceService;
	
	@RequestMapping(value="/pic/upload")
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile) {
		PictureResult picResult = staticResourceService.uploadPic(uploadFile);
		return JsonUtils.objectToJson(picResult);
	}
}
