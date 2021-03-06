package com.aube.json.msg;

import com.aube.mongo.support.MGObject;

/**
 * TODO 分表<br>
 * 普通消息<br>
 * {msg:"Hello",extra:{msgID:"UID3434343034",headImg:"",videoTime:"",msgType:
 * "normal"}} 点赞消息<br>
 * {msg:"UID3434343034",extra:{msgType:"like"}}
 */
public class MessageByVideoid extends MGObject<MessageByVideoid> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5846637608930744391L;
	// 消息ID
	private String msgId;
	// 发送者
	private String fromUserId;
	// 接收者
	private String toUserId;
	// 视频ID
	private String videoid;
	// 消息类型
	private String msgType;
	// 发送时间
	private Long timestamp;
	private String timestampStr;
	// 消息内容
	private String content;
	// 发送者头像
	private String headImg;
	// 用户昵称
	private String nickname;
	// 相对视频的时间，单位（秒）
	private Integer videoTime;
	private String channelType;
	private Integer praiseCount;

	public MessageByVideoid() {
		this.praiseCount = 0;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	// 来源（环信OR融云）
	private String sources;

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

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

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestampStr() {
		return timestampStr;
	}

	public void setTimestampStr(String timestampStr) {
		this.timestampStr = timestampStr;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
