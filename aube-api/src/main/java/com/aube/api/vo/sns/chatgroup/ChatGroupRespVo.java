package com.aube.api.vo.sns.chatgroup;

import com.aube.vo.GsonObject;

public class ChatGroupRespVo implements GsonObject<ChatGroupRespVo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8530914709583677656L;
	private String userId;
	private String groupId;
	private String groupName;
	private String appKey;
	private String appSources;
	private String videoId;
	private String status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSources() {
		return appSources;
	}

	public void setAppSources(String appSources) {
		this.appSources = appSources;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
