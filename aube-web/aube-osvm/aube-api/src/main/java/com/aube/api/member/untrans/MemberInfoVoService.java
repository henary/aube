package com.aube.api.member.untrans;

import com.aube.api.vo.member.MemberInfoVo;
import com.aube.constants.ucenter.OpensourceEnum;
import com.aube.support.ResultCode;

public interface MemberInfoVoService {
	/**
	 * 根据第三方渠道的token，查询用户，存在直接返回，不存在创建之后返回
	 * 
	 * @param opensources
	 *            第三方渠道号
	 * @param token
	 *            第三方授权的token
	 * @return
	 */
	ResultCode<MemberInfoVo> getMemberInfoByOpensource(OpensourceEnum opensources, String token);

}
