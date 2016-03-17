package com.aube.web.action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constans.MongoData;
import com.aube.json.common.member.MemberInfo;
import com.aube.resp.vo.DataRespVo;
import com.aube.support.ResultCode;
import com.aube.util.JsonUtils;
import com.aube.util.LoginUtils;
import com.aube.util.StringUtil;
import com.aube.util.VoCopyUtil;
import com.aube.vo.member.MemberInfoVo;
import com.aube.web.action.AubeH5BaseController;

/**
 * 用户登陆<br>
 * service后期移入dubbo服务中
 */
@Controller
public class MemberController extends AubeH5BaseController {
	
	@RequestMapping("/member/testLogin.xhtml")
	@ResponseBody
	public String commonLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO 后续加入用户登陆的cookie移入dubbo中处理
		String userTrace = LoginUtils.setTraceIfNotExists(request, response);
		MemberInfo memberInfo = mongoService.getObjectById(MemberInfo.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, userTrace);
		if (memberInfo == null) {
			memberInfo = new MemberInfo();
			memberInfo.set_id(userTrace);
			memberInfo.setNickname(StringUtil.getRandomString(8));
			mongoService.saveOrUpdateObject(memberInfo, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		}
		ResultCode<MemberInfoVo> infoVoCode  = VoCopyUtil.copyProperties(MemberInfoVo.class, memberInfo);
		DataRespVo<MemberInfoVo> respVo = new DataRespVo<MemberInfoVo>();
		if (infoVoCode.isSuccess()) {
			MemberInfoVo infoVo = infoVoCode.getRetval();
			infoVo.setHeadPic(config.getStaticPath() + infoVo.getHeadPic());
			respVo.setResult(infoVo);
		} else {
			respVo.setErrCode(infoVoCode.getErrcode());
			respVo.setErrMsg(infoVoCode.getMsg());
		}
		return JsonUtils.writeObjectToJson(respVo);
	}
	
	
	
}
