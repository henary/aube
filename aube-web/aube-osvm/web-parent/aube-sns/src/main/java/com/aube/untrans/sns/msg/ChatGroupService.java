package com.aube.untrans.sns.msg;

import com.aube.api.vo.sns.chatgroup.ChatGroupRespVo;
import com.aube.support.ResultCode;

public interface ChatGroupService {

	/**
	 * 加入聊天室，如果没有则创建
	 * 
	 * @param userId
	 *            用户标识
	 * @param videoId
	 *            视频ID
	 * @return
	 */
	ResultCode<ChatGroupRespVo> joinGroup(String userId, String videoId);

}
