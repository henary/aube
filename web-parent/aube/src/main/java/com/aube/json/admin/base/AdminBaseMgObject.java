package com.aube.json.admin.base;

import com.aube.mongo.support.MGObject;

public class AdminBaseMgObject<T> extends MGObject<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -304669775445588839L;
	public static final String SORT_KEY_ADDTIME = "addTime";
	// 节目的appkey
	private String appKey;
	// groupId
	private String groupId;
	// 添加人ID
	private String userId;
	// 添加时间
	private String addTime;
	// 修改时间
	private String updTime;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}
}
