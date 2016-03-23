package com.aube.untrans.common.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aube.Config;
import com.aube.constant.ErrorCodeConstant;
import com.aube.resp.vo.pic.PicInfoRespVo;
import com.aube.support.ResultCode;
import com.aube.untrans.common.AubePicService;
import com.aube.util.PictureUtils;
import com.aube.util.StringUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

@Service("aubePicService")
public class AubePicServiceImpl implements AubePicService, InitializingBean {
	protected transient AubeLogger logger = LoggerUtils.getLogger(AubePicServiceImpl.class);

	@Autowired
	@Qualifier("config")
	private Config config;
	private String tempPath; // 临时文件

	private static final List<String> PIC_TAG_LIST = Arrays.asList(new String[] { "show", "video" });

	@Override
	public ResultCode<PicInfoRespVo> saveToTempPic(MultipartFile file, String picTag, String relatedId) {
		try {
			if (!PIC_TAG_LIST.contains(picTag)) {
				return ResultCode.<PicInfoRespVo> getFailure(ErrorCodeConstant.CODE_UPLOAD_NOT_SUPPORTS_TAG);
			}
			String extname = StringUtil.getFilenameExtension(file.getOriginalFilename());
			if (StringUtils.equals("jpeg", extname))
				extname = "jpg";
			String fileName = StringUtil.getUID() + "." + extname;
			File upfile = new File(tempPath + picTag, fileName);
			file.transferTo(upfile);
			PicInfoRespVo respVo = PictureUtils.getPicDim(tempPath + picTag + "/" + fileName);
			respVo.setPicurl(PictureUtils.getCommonPicpath() + fileName);
			respVo.setPicRelatedId(relatedId);
			return ResultCode.<PicInfoRespVo> getSuccessReturn(respVo);
		} catch (Exception e) {
			logger.error(e, 20);
			return ResultCode.<PicInfoRespVo> getFailure(ErrorCodeConstant.CODE_UNKNOWN_ERROR, e.getMessage());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String picPath = config.getString("tempPicPath");
		File tmpFile = new File(picPath, PictureUtils.getCommonPicpath());
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		tempPath = tmpFile.getAbsolutePath();
	}
}
