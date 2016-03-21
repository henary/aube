package com.aube.web.action.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.api.sns.msg.untrans.MsgPraiseVoService;
import com.aube.api.vo.sns.msgpraise.MsgPraiseRespVo;
import com.aube.support.ResultCode;
import com.aube.util.JsonUtils;
import com.aube.web.action.AubeH5BaseController;


@Controller
public class MsgPraiseController extends AubeH5BaseController {
	@Autowired@Qualifier("msgPraiseVoService")
	private MsgPraiseVoService msgPraiseVoService;
	@RequestMapping("/msgparise.xhtml")
	@ResponseBody
	public String msgparise(String videoid, Integer from, Integer maxnum) {
		msgPraiseVoService.praisemsg(videoid, "111");
		
		ResultCode<List<MsgPraiseRespVo>> respVoCode = msgPraiseVoService.getMsgPraiseList(videoid, from, maxnum);
		return JsonUtils.writeObjectToJson(respVoCode);
	}
}
