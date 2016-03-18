package com.aube.untrans.member;

import com.aube.constants.ucenter.OpensourceEnum;
import com.aube.json.ucenter.member.MemberInfo;
import com.aube.support.ResultCode;

public interface MemberService {

	/**
	 * 根据第三方渠道的token，查询用户，存在直接返回，不存在创建之后返回
	 * 
	 * @param opensources
	 *            第三方渠道号
	 * @param token
	 *            第三方授权的token
	 * @return
	 */
	ResultCode<MemberInfo> getMemberInfoByOpensource(OpensourceEnum opensources, String token);

	/**
	 * 根据手机号获取用户
	 * 
	 * @param mobile
	 * @param password
	 * @return
	 */
	ResultCode<MemberInfo> getMemberInfoByMobile(String mobile, String password);

	

}
