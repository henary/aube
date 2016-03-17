package com.aube.vo.member;

import org.apache.commons.lang.StringUtils;

import com.aube.constants.MemberConstants;
import com.aube.json.common.member.MemberInfo;

/**
 * 用户登陆返回的信息
 *
 */
public class MemberInfoVo extends MemberInfo  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6634288278500879103L;
	// 融云token
	private String rctoken;
	
	@Override
	public String getHeadPic() {
		if (StringUtils.isBlank(super.getHeadPic())) {
			return MemberConstants.DEFAULT_HEAD_IMG;
		}
		return super.getHeadPic();
	}
	public String getRctoken() {
		return rctoken;
	}
	public void setRctoken(String rctoken) {
		this.rctoken = rctoken;
	}
	

}
