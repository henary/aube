package com.aube.web.action.sns;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.json.common.member.MemberInfo;
import com.aube.json.common.member.sns.Member4GroupIdByVideo;
import com.aube.json.common.member.sns.Member4RongcloudToken;
import com.aube.resp.vo.DataRespVo;
import com.aube.support.ResultCode;
import com.aube.util.JsonUtils;
import com.aube.util.LoginUtils;
import com.aube.util.RongcloudUtils;
import com.aube.util.StringUtil;
import com.aube.vo.rongcloud.RongcloudMemberToken;
import com.aube.web.action.AubeH5BaseController;

@Controller
public class RongcloudController extends AubeH5BaseController {

	@RequestMapping("/member/getToken.xhtml")
	@ResponseBody
	public String getToken(String videoid, HttpServletRequest request, HttpServletResponse response) {
		// TODO 用户信息获取，不用usertrace
		DataRespVo<String> respVo = new DataRespVo<String>();
		String userTrace = LoginUtils.setTraceIfNotExists(request, response);
		
		Member4RongcloudToken mapping = mongoService.getObjectById(Member4RongcloudToken.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, userTrace);
		if (mapping == null) {
			MemberInfo memberInfo = mongoService.getObjectById(MemberInfo.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, userTrace);
			ResultCode<RongcloudMemberToken> addGroupCode = RongcloudUtils.getTokenByMember(memberInfo, config.getStaticPath());
			if (!addGroupCode.isSuccess()) {
				respVo.setErrCode(addGroupCode.getErrcode());
				respVo.setErrMsg(addGroupCode.getMsg());
				return JsonUtils.writeObjectToJson(respVo);
			}
			RongcloudMemberToken token = addGroupCode.getRetval();
			// TODO 用jms中处理
			Member4RongcloudToken mappingToken = new Member4RongcloudToken();
			mappingToken.set_id(memberInfo.realId());
			mappingToken.setMemberId(memberInfo.realId()+"");
			mappingToken.setToken(token.getToken());
			mongoService.saveOrUpdateObject(mappingToken, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
			// TODO JMS中处理
			String id = StringUtil.md5(videoid + userTrace);
			Member4GroupIdByVideo groupIdByVideo = mongoService.getObjectById(Member4GroupIdByVideo.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, id);
			if (groupIdByVideo == null) {
				// group id计数
				RongcloudUtils.add2group(userTrace, videoid, "videoid测试");
			}
		}
		respVo.setResult(mapping.getToken());
		return JsonUtils.writeObjectToJson(respVo);
	}
}
