package com.aube.api.sns.chat.untrans.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.api.sns.chat.untrans.ChatGroupVoService;
import com.aube.api.vo.sns.chatgroup.ChatGroupRespVo;
import com.aube.support.ResultCode;
import com.aube.untrans.sns.msg.ChatGroupService;

public class ChatGroupVoServiceImpl implements ChatGroupVoService {
	@Autowired@Qualifier("chatGroupService")
	private ChatGroupService chatGroupService;
	
	
	@Override
	public ResultCode<ChatGroupRespVo> joinGroup(String userId, String videoId) {
		return chatGroupService.joinGroup(userId, videoId);
	}
}
