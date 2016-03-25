package com.aube.util;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.StringUtils;
import org.im4java.core.Info;

import com.aube.resp.vo.pic.PicInfoRespVo;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class PictureUtils {
	private static final AubeLogger logger = LoggerUtils.getLogger(PictureUtils.class);
	private static final List<String> validPicExt = Arrays.asList("jpg", "jpeg", "gif", "png");
	public static final String UPLOADTYPE_PIC = "pic";
	public static final String UPLOADTYPE_VIDEO = "video";

	public static boolean isValidPicExt(String file) {
		return validPicExt.contains(StringUtil.getFilenameExtension(file));
	}

	public static boolean isValidExtension(String file, String type) {
		String ext = StringUtil.getFilenameExtension(file);
		if (UPLOADTYPE_PIC.equals(type))
			return validPicExt.contains(ext);
		return false;
	}

	public static String getPicFormat(File f) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(f);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return null;
			}
			ImageReader reader = iter.next();
			iis.close();
			String result = StringUtils.lowerCase(reader.getFormatName());
			if (StringUtils.equals("jpeg", result))
				return "jpg";
			return result;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public static PicInfoRespVo getPicDim(String src) {
		PicInfoRespVo respVo = new PicInfoRespVo();
		try {
			Info imageInfo = new Info(src, true);
			int srcWidth = imageInfo.getImageWidth();
			int srcHeight = imageInfo.getImageHeight();
			respVo.setHeight(srcHeight);
			respVo.setWidth(srcWidth);
			return respVo;
		} catch (Exception e) {
			logger.error(e, 2);
			return respVo;
		}
	}

	public static String getCommonPicpath(String picTag) {
		return "images/" + DateUtil.format(DateUtil.getCurDate(), "yyyyMM") + "/" + picTag + "/";
	}
	
	public static String getVideopath() {
		return "videos/" + DateUtil.format(DateUtil.getCurDate(), "yyyyMM") + "/video/";
	}
}
