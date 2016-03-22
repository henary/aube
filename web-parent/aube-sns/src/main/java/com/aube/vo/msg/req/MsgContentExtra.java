package com.aube.vo.msg.req;

import java.io.Serializable;

public class MsgContentExtra implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1841821720005327069L;
	private String headImg;
	private Integer videoTime;
	private String msgType;
	private String nickname;

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(Integer videoTime) {
		this.videoTime = videoTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
