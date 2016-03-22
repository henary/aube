package com.aube.vo;

public class ApiRequestParams implements GsonObject<ApiRequestParams> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1092832639134278994L;
	private String paramName;
	private String paramType;
	private String paramsMust;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamsMust() {
		return paramsMust;
	}

	public void setParamsMust(String paramsMust) {
		this.paramsMust = paramsMust;
	}

}
