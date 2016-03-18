package com.aube.json.ucenter.member;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.aube.constants.ucenter.MemberConstants;
import com.aube.mongo.support.MGObject;

/**
 * 用户基础信息 <br>
 * 索引1:opensources、openid<br>
 * 索引2:mobile _id:如果是联名登录的则hash(opensources+openid)<br>
 * 手机号注册的为：hash(mobile) <br>
 * TODO 后续的手机号关联联名登录的用户？？
 */
public class MemberInfo extends MGObject<MemberInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6850362353286286866L;
	// 用户手机号
	private String mobile;
	// 密码
	private String memberpass;
	// 用户昵称
	private String nickname;
	// 用户头像
	private String headPic;
	// 用户来源
	private String opensources;
	// 第三方唯一标识
	private String openid;
	// 联名登陆失效日期
	private Timestamp invalidDate;

	public MemberInfo() {

	}

	public MemberInfo(String opensources, String openid) {
		this.openid = openid;
		this.opensources = opensources;
	}

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

	public String getMemberpass() {
		return memberpass;
	}

	public void setMemberpass(String memberpass) {
		this.memberpass = memberpass;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
