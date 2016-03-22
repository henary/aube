package com.aube.json.openapi;

import com.aube.mongo.support.MGObject;

/**
 * Appkey基础信息
 * 
 *
 */
public class ConsumerInfo extends MGObject<ConsumerInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4026943719151994950L;
	// App名称
	private String appName;
	// appkey
	private String appKey;
	// 密钥
	private String secretCode;
	// 描述
	private String desc;
	// 审核状态
	private String audited;
	// 添加时间
	private String addTime;
	// 其他信息
	private String otherinfo;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAudited() {
		return audited;
	}

	public void setAudited(String audited) {
		this.audited = audited;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getOtherinfo() {
		return otherinfo;
	}

	public void setOtherinfo(String otherinfo) {
		this.otherinfo = otherinfo;
	}

}
