package com.aube.web.action.notify;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.untrans.sns.msg.RongcloudService;
import com.aube.util.BaseWebUtils;
import com.aube.vo.rongcloud.msg.req.RongcloudMsgReqVo;
import com.aube.web.action.AubeSnsBaseController;

@Controller
public class RongCloudNotifyController extends AubeSnsBaseController  {
	
	
	@Autowired@Qualifier("rongcloudService")
	private RongcloudService rongcloudService;
	
	
	@RequestMapping("/rongcloud/notify/apply.xhtml")
	@ResponseBody
	public String notifyApply(RongcloudMsgReqVo reqVo, HttpServletRequest request) {
		Map<String, String> reqHeadMap = BaseWebUtils.getHeaderMap(request);
		logger.warn("header:" + reqHeadMap);
		logger.warn("body:" + BaseWebUtils.getRequestMap(request));
		rongcloudService.saveRongcloudMsg(reqVo);
		return "OK";
	}
}
