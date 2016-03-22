package com.aube.json.openapi;

import com.aube.mongo.support.MGObject;

/**
 * API接口信息
 * 
 * @see com.aube.vo.ApiRequestParams
 */
public class ApiInfo extends MGObject<ApiInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1704580273815742120L;
	// api接口method
	private String apiMethod;
	// api接口名称
	private String apiName;
	// api接口对应的真实地址
	private String realURL;
	// 参数限制
	private String reqParams;
	// 添加时间
	private String addTime;
	// api接口描述
	private String apiDesc;
	// 返回值示例
	private String returnDesc;
	// 其他描述
	private String otherDesc;

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getRealURL() {
		return realURL;
	}

	public void setRealURL(String realURL) {
		this.realURL = realURL;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getApiDesc() {
		return apiDesc;
	}

	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}

	public String getReturnDesc() {
		return returnDesc;
	}

	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public String getReqParams() {
		return reqParams;
	}

	public void setReqParams(String reqParams) {
		this.reqParams = reqParams;
	}

	public String getApiMethod() {
		return apiMethod;
	}

	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}

}
