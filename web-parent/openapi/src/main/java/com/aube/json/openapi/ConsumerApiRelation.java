package com.aube.json.openapi;

import com.aube.mongo.support.MGObject;

/**
 * Appkey的接口调用权限
 *
 */
public class ConsumerApiRelation extends MGObject<ConsumerApiRelation> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8657186211357610298L;
	// api接口method
	private String apiMethod;
	// appkey
	private String appKey;
	// 添加时间
	private String addTime;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getApiMethod() {
		return apiMethod;
	}

	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
}
