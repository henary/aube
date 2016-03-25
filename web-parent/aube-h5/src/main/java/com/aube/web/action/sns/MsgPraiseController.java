package com.aube.web.action.sns;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.api.sns.chat.untrans.ChatGroupVoService;
import com.aube.api.sns.msg.untrans.MsgPraiseVoService;
import com.aube.api.vo.sns.chatgroup.ChatGroupRespVo;
import com.aube.api.vo.sns.message.MessageVo;
import com.aube.api.vo.sns.msgpraise.MsgPraiseRespVo;
import com.aube.support.ResultCode;
import com.aube.util.JsonUtils;
import com.aube.web.action.AubeH5BaseController;

@Controller
public class MsgPraiseController extends AubeH5BaseController {
	@Autowired
	@Qualifier("msgPraiseVoService")
	private MsgPraiseVoService msgPraiseVoService;

	@Autowired
	@Qualifier("chatGroupVoService")
	private ChatGroupVoService chatGroupVoService;

	@RequestMapping("/msgparise.xhtml")
	@ResponseBody
	public String msgparise(String videoid, Integer from, Integer maxnum) {
		msgPraiseVoService.praisemsg(videoid, "111");

		ResultCode<List<MsgPraiseRespVo>> respVoCode = msgPraiseVoService.getMsgPraiseList(videoid, from, maxnum);
		return JsonUtils.writeObjectToJson(respVoCode);
	}

	@RequestMapping("/joinGroup.xhtml")
	@ResponseBody
	public String joinGroup(String userid, String videoid) {
		ResultCode<ChatGroupRespVo> respVoCode = chatGroupVoService.joinGroup(userid, videoid);
		return JsonUtils.writeGsonWithPretty(respVoCode, true);
	}
	
	@RequestMapping("/msgbygroupid.xhtml")
	@ResponseBody
	public String msgbygroupid(String groupid) {
		ResultCode<List<MessageVo>>  msgListCode = msgPraiseVoService.getMssageListByGroupId(groupid, 0, 1);
		return JsonUtils.writeGsonWithPretty(msgListCode, true);
	}
}
