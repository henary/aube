package com.aube.web.action.admin.video;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.constants.AubeConfigConstants;
import com.aube.support.ResultCode;
import com.aube.untrans.AubeConfigService;
import com.aube.util.CCSginUtils;
import com.aube.util.JsonUtils;
import com.aube.vo.BokeccConfigVo;
import com.aube.web.action.admin.BaseAdminController;

@Controller
public class BokeccUploadAdminController extends BaseAdminController {
	@Autowired@Qualifier("aubeConfigService")
	private AubeConfigService aubeConfigService;
	
	@RequestMapping("/admin/video/upload/uploadMc.xhtml")
	@ResponseBody
	public String uploadMc(String videoTitle, String videoDesc) {
		ResultCode<String> ccCfgCode = aubeConfigService.getAubeConfig(AubeConfigConstants.CC_CONFIG);
		if (!ccCfgCode.isSuccess()) {
			return result2Json(ccCfgCode);
		}
		BokeccConfigVo configVo = JsonUtils.parseObj(ccCfgCode.getRetval(), BokeccConfigVo.class);
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("description", videoDesc);
		params.put("tag", "multicamera");
		params.put("title", videoTitle);
		params.put("userid", configVo.getAppkey());
		return CCSginUtils.getCCUploadSginStr(params, configVo.getAppSecret());
	}
}
