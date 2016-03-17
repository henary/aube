package com.aube.vo.rongcloud;

import com.aube.vo.GsonObject;

public class RongcloudBaseResult implements GsonObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3417390542359547918L;
	private String url;
	private Integer code;
	private String errorMessage;

	public boolean isSuccess() {
		return code.equals(200);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
