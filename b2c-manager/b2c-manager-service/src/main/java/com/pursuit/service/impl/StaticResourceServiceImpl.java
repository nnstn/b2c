package com.pursuit.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pursuit.common.PictureResult;
import com.pursuit.fastdfs.util.FastDFSClient;
import com.pursuit.service.StaticResourceService;
@Service
public class StaticResourceServiceImpl implements StaticResourceService {
	
	@Value(value="${nginx_server}")
	private String nginx_server;
	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		//判断图片是否为空
		if (picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		//上传到图片服务器
		try {
			//取图片扩展名
			String originalFilename = picFile.getOriginalFilename();
			//取扩展名不要“.”
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			
			FastDFSClient client = new FastDFSClient("classpath:properties/client.properties");
			String url = client.upload_file(picFile.getBytes(),extName);
			url = nginx_server+url;
			//把url响应给客户端
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			result.setMessage("图片上传失败");
		}
		return result;
	}

}
