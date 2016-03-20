package com.aube.untrans.member.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.aube.constans.MongoData;
import com.aube.constant.ErrorCodeConstant;
import com.aube.constants.ucenter.OpensourceEnum;
import com.aube.json.ucenter.member.MemberInfo;
import com.aube.service.BaseService;
import com.aube.support.ResultCode;
import com.aube.untrans.member.MemberService;
import com.aube.util.StringUtil;


/**
 * TODO token去第三方验证
 *
 */
@Service("memberService")
public class MemberServiceImpl extends BaseService implements MemberService {

	@Override
	public ResultCode<MemberInfo> getMemberInfoByOpensource(OpensourceEnum opensources, String token) {
		String memberid = StringUtil.md5(opensources + token);
		MemberInfo memberInfo = mongoService.getObjectById(MemberInfo.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM,
				memberid);
		if (memberInfo != null) {
			return ResultCode.<MemberInfo> getSuccessReturn(memberInfo);
		}
		memberInfo = new MemberInfo(opensources.toString(), token);
		memberInfo.set_id(memberid);
		memberInfo.setMemberpass(getMemberDefaultPass());
		mongoService.saveOrUpdateObject(memberInfo, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		return ResultCode.<MemberInfo> getSuccessReturn(memberInfo);
	}

	@Override
	public ResultCode<MemberInfo> getMemberInfoByMobile(String mobile, String password) {
		MemberInfo memberInfo = mongoService.getObjectById(MemberInfo.class, "mobile", mobile);
		if (memberInfo == null) {
			return ResultCode.<MemberInfo> getFailure(ErrorCodeConstant.CODE_ACCOUNT_NOT_EXIST);
		}
		String signPass = afterSignPass(password);
		if (!StringUtils.equals(signPass, memberInfo.getMemberpass())) {
			return ResultCode.<MemberInfo> getFailure(ErrorCodeConstant.CODE_ACCOUNT_PASS_ERROR);
		}
		return ResultCode.<MemberInfo> getSuccessReturn(memberInfo);
	}

	private String getMemberDefaultPass() {
		String password = StringUtil.getRandomString(8);
		return afterSignPass(password);
	}

	private String afterSignPass(String password) {
		String passsalt = config.getString("passsalt");
		String pass = StringUtil.md5(StringUtil.getRandomString(8) + passsalt);
		return StringUtil.md5(pass + passsalt);
	}
}
