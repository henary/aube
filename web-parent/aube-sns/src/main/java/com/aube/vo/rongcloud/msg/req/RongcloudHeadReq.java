package com.aube.vo.rongcloud.msg.req;

import java.io.Serializable;

/**
 * 请求融云的签名header
 * 
 * @see http://www.rongcloud.cn/docs/server.html#通用_API_接口签名规则
 *
 */
public class RongcloudHeadReq implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5131517423847221379L;
	// appkey
	private String appKey;
	// 时间戳（秒）
	private Long timestamp;
	// 随机数
	private String nonce;
	// 签名
	private String signature;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
