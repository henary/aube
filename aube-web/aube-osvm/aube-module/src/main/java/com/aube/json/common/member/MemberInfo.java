package com.aube.json.common.member;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.aube.constants.MemberConstants;
import com.aube.mongo.support.MGObject;

/**
 * 用户个人信息<br>
 * TODO 后期移到dubbo后台服务中处理
 */
public class MemberInfo extends MGObject<MemberInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6850362353286286866L;
	// 用户手机号
	private String mobile;
	// 用户昵称
	private String nickname;
	// 用户头像
	private String headPic;
	// 用户来源
	private String opensources;
	// 联名登陆失效日期
	private Timestamp invalidDate;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadPic() {
		if (StringUtils.isBlank(headPic)) {
			return MemberConstants.DEFAULT_HEAD_IMG;
		}
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getOpensources() {
		return opensources;
	}

	public void setOpensources(String opensources) {
		this.opensources = opensources;
	}

	public Timestamp getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}

}
