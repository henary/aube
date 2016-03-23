package com.aube.untrans.common;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.aube.resp.vo.pic.PicInfoRespVo;
import com.aube.support.ResultCode;

public interface AubePicService {

	/**
	 * 上传文件到服务器
	 * 
	 * @param file
	 * @param picTag
	 * @param relaredId
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	ResultCode<PicInfoRespVo> saveToTempPic(MultipartFile file, String picTag, String relaredId);

}
