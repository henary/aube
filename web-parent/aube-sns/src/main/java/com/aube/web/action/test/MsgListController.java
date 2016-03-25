package com.aube.web.action.test;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.json.msg.MessageByVideoid;
import com.aube.mdb.operation.Expression;
import com.aube.util.JsonUtils;
import com.aube.web.action.AubeSnsBaseController;


@Controller
public class MsgListController extends AubeSnsBaseController {
	@RequestMapping("/test/msglist.xhtml")
	@ResponseBody
	public String msgList(String groupid) {
		Expression params = new Expression();
		params.eq("toUserId", groupid);
		List<MessageByVideoid> messageList = mongoService.getObjectList(MessageByVideoid.class, params);
		return JsonUtils.writeGsonWithPretty(messageList, true);
	}
	
	
	@RequestMapping("/test/allmsglist.xhtml")
	@ResponseBody
	public String allmsglist(Integer from, Integer maxnum) {
		Expression params = new Expression();
		List<MessageByVideoid> messageList = mongoService.getObjectList(MessageByVideoid.class, params, "timestamp", true, from, maxnum);
		return JsonUtils.writeGsonWithPretty(messageList, true);
	}
	
}
