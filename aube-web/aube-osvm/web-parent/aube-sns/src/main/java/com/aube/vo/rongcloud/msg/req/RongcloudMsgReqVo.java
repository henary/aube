package com.aube.vo.rongcloud.msg.req;

import java.io.Serializable;

/**
 * 融云消息路由服务
 * 
 * @see http://www.rongcloud.cn/docs/server.html#消息历史记录服务
 *
 */
public class RongcloudMsgReqVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3878414127411402310L;
	// 发送用户 Id
	private String fromUserId;
	// 接收用户 Id，即为客户端 targetId
	private String toUserId;
	// 消息类型
	private String objectName;
	// 发送消息内容
	private Long timestamp;
	// 服务端收到客户端发送消息时的服务器时间（1970年到现在的毫秒数）。
	private String content;
	// 会话类型
	private String channelType;
	// timestamp 的别名，主要为兼容一些外部框架使用（1970年到现在的毫秒数）。
	private Long msgTimestamp;
	// 可通过 msgUID 确定消息唯一。
	private String msgUID;

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

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public Long getMsgTimestamp() {
		return msgTimestamp;
	}

	public void setMsgTimestamp(Long msgTimestamp) {
		this.msgTimestamp = msgTimestamp;
	}

	public String getMsgUID() {
		return msgUID;
	}

	public void setMsgUID(String msgUID) {
		this.msgUID = msgUID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
