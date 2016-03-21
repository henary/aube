package com.aube.json.chat;

import com.aube.mongo.support.MGObject;

/**
 * 聊天服务应用详情<br>
 * 主键_id为rongcloud、huanxin
 * 
 *
 */
public class ChatAppInfo extends MGObject<ChatAppInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8348844603670619393L;
	private String appKey;
	private String appSecret;
	// 一个房间最多多少人
	private Integer maxRunners;

	public ChatAppInfo() {

	}

	public ChatAppInfo(String appSources, String appKey, String appSecret) {
		set_id(appSources);
		this.appKey = appKey;
		this.appSecret = appSecret;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Integer getMaxRunners() {
		return maxRunners;
	}

	public void setMaxRunners(Integer maxRunners) {
		this.maxRunners = maxRunners;
	}

}
