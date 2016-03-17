package com.aube.json.common.member.sns;

import java.sql.Timestamp;

import com.aube.mongo.support.MGObject;

/**
 * 融云和token和用户的对应关系<br>
 * TODO 后期移到dubbo后台服务中处理
 */
public class Member4RongcloudToken extends MGObject<Member4RongcloudToken> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936043646968129553L;
	private String memberId;
	// 融云token
	private String token;
	// 用户唯一标识(ios：idfa,android：deviceid,h5:随机数)
	private String userTract;
	// 失效日期
	private Timestamp invalidDate;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserTract() {
		return userTract;
	}

	public void setUserTract(String userTract) {
		this.userTract = userTract;
	}

	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

}
