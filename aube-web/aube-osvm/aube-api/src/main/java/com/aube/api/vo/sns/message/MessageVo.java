package com.aube.api.vo.sns.message;

import com.aube.vo.GsonObject;

/***
 * 群组聊天消息
 */
public class MessageVo implements GsonObject<MessageVo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 443053849765816864L;
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
	private String contnet;
	// 发送者头像
	private String headImg;
	// 用户昵称
	private String nickname;
	// 相对视频的时间，单位（秒）
	private Integer videoTime;
	// 频道（单聊、群聊……）
	private String channelType;
	// 点赞数
	private Integer praiseCount;

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

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
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

	public String getContnet() {
		return contnet;
	}

	public void setContnet(String contnet) {
		this.contnet = contnet;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getVideoTime() {
		return videoTime;
	}

	public void setVideoTime(Integer videoTime) {
		this.videoTime = videoTime;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
