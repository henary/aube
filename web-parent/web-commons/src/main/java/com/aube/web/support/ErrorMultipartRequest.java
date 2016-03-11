package com.aube.web.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

public class ErrorMultipartRequest extends AbstractMultipartHttpServletRequest implements MultipartHttpServletRequest {

	private String errorMsg;

	public ErrorMultipartRequest(HttpServletRequest request, String errorMsg) {
		super(request);
		setMultipartFiles(new LinkedMultiValueMap<String, MultipartFile>());

		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String getMultipartContentType(String paramOrFileName) {
		// TODO 
		return null;
	}

	@Override
	public HttpHeaders getMultipartHeaders(String paramOrFileName) {
		// TODO 
		return null;
	}
}
