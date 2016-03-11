package com.aube.web.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.aube.Config;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class AubeMultipartResolver extends CommonsMultipartResolver{
	private final transient AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);
	@Override
	public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
		Assert.notNull(request, "Request must not be null");
		if(!isMultipart(request)) return new ErrorMultipartRequest(request, "请重新上传文件");
		try{
			MultipartParsingResult parsingResult = parseRequest(request);
			return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(), 
					parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes());
		}catch(MaxUploadSizeExceededException e){
			return new ErrorMultipartRequest(request, "文件大小超出范围");
		}catch(Exception e){
			dbLogger.warn("", e);
			return new ErrorMultipartRequest(request, "上传出现错误");
		}
	}
}
