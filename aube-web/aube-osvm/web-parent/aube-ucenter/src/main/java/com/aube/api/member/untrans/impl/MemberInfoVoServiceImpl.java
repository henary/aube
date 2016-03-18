package com.aube.api.member.untrans.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.api.member.untrans.MemberInfoVoService;
import com.aube.api.vo.member.MemberInfoVo;
import com.aube.constants.ucenter.OpensourceEnum;
import com.aube.json.ucenter.member.MemberInfo;
import com.aube.support.ResultCode;
import com.aube.untrans.member.MemberService;
import com.aube.util.VoCopyUtil;

public class MemberInfoVoServiceImpl implements MemberInfoVoService {
	@Autowired@Qualifier("memberService")
	private MemberService memberService;

	@Override
	public ResultCode<MemberInfoVo> getMemberInfoByOpensource(OpensourceEnum opensources, String token) {
		ResultCode<MemberInfo> memberInfoCode = memberService.getMemberInfoByOpensource(opensources, token);
		if (!memberInfoCode.isSuccess()) {
			return ResultCode.<MemberInfoVo>getFailure(memberInfoCode.getErrcode(), memberInfoCode.getMsg());
		}
		return VoCopyUtil.copyProperties(MemberInfoVo.class, memberInfoCode.getRetval());
	}
}
