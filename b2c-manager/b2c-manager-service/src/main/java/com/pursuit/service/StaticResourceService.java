package com.pursuit.service;

import org.springframework.web.multipart.MultipartFile;
import com.pursuit.common.PictureResult;

public interface StaticResourceService {
	public PictureResult uploadPic(MultipartFile uploadFile);
}
