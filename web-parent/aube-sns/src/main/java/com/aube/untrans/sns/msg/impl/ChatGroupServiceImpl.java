package com.aube.untrans.sns.msg.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aube.api.vo.sns.chatgroup.ChatGroupRespVo;
import com.aube.constant.ErrorCodeConstant;
import com.aube.constants.ChatConstants;
import com.aube.constants.sns.SnsMessageConstants;
import com.aube.json.chat.ChatAppInfo;
import com.aube.service.BaseService;
import com.aube.support.ResultCode;
import com.aube.untrans.AubeSnsConfigService;
import com.aube.untrans.sns.msg.ChatGroupService;
import com.aube.util.BeanUtil;
import com.aube.util.HttpResult;
import com.aube.util.HttpUtils;
import com.aube.util.StringUtil;

@Service("chatGroupService")
public class ChatGroupServiceImpl extends BaseService implements ChatGroupService {
	@Autowired
	@Qualifier("aubeSnsConfigService")
	private AubeSnsConfigService aubeSnsConfigService;

	// TODO 移到SSDB或redis中,聊天室限制人数，超出限制重新创建聊天室ID
	private Map<String/** videoid */
	, String/** groupid */
	> GROUP_VIDEO_MAP = new HashMap<String, String>();

	@Override
	public ResultCode<ChatGroupRespVo> joinGroup(String userId, String videoId) {
		ResultCode<ChatAppInfo> chatAppInfoCode = aubeSnsConfigService.getUsedChatAppinfo();
		if (!chatAppInfoCode.isSuccess()) {
			return ResultCode.<ChatGroupRespVo> getFailure(chatAppInfoCode.getErrcode());
		}
		ChatAppInfo chatAppInfo = chatAppInfoCode.getRetval();
		String groupId = GROUP_VIDEO_MAP.get(videoId);
		if (StringUtils.isBlank(GROUP_VIDEO_MAP.get(videoId))) {
			groupId = videoId + "_" + StringUtil.getRandomString(5);
			GROUP_VIDEO_MAP.put(videoId, groupId);
			return createChatGroup(userId, videoId, groupId, chatAppInfo);
		}
		if (StringUtils.equals(chatAppInfo.realId().toString(), SnsMessageConstants.MESSAGE_SOURCE_RONGCLOUD)) {
			return joinRongcloudGroup(userId, videoId, groupId, chatAppInfo, true);
		} else {
			return ResultCode.<ChatGroupRespVo> getFailure(ErrorCodeConstant.CODE_CHAT_APP_NOT_SUPPORT);
		}
	}

	private ResultCode<ChatGroupRespVo> createChatGroup(String userId, String videoId, String groupId, ChatAppInfo chatAppInfo) {
		if (StringUtils.equals(chatAppInfo.realId().toString(), SnsMessageConstants.MESSAGE_SOURCE_RONGCLOUD)) {
			return joinRongcloudGroup(userId, videoId, groupId, chatAppInfo, false);
		} else {
			return ResultCode.<ChatGroupRespVo> getFailure(ErrorCodeConstant.CODE_CHAT_APP_NOT_SUPPORT);
		}
	}

	private ResultCode<ChatGroupRespVo> joinRongcloudGroup(String userId, String videoId, String groupId, ChatAppInfo chatAppInfo, boolean isJoin) {
		Map<String, String> reqHeader = getRongyunReqHeader(chatAppInfo);
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);
		params.put("groupId", groupId);
		params.put("groupName", groupId);
		HttpResult result = HttpUtils.postUrlAsString(isJoin ? ChatConstants.RONGCLOUD_GROUP_JOIN_URL : ChatConstants.RONGCLOUD_GROUP_CREATE_URL, params,
				reqHeader, "UTF-8");
		if (result.isSuccess()) {
			ChatGroupRespVo respVo = new ChatGroupRespVo();
			BeanUtil.copyProperties(respVo, params);
			respVo.setAppKey(chatAppInfo.getAppKey());
			respVo.setAppSources(chatAppInfo.realId().toString());
			respVo.setVideoId(videoId);
			return ResultCode.<ChatGroupRespVo> getSuccessReturn(respVo);
		}
		return ResultCode.<ChatGroupRespVo> getFailure(result.getMsg());
	}

	private Map<String, String> getRongyunReqHeader(ChatAppInfo chatAppInfo) {
		Map<String, String> reqHeader = new HashMap<String, String>();
		String nonce = StringUtil.getRandomString(5);
		Long timestamp = System.currentTimeMillis() / 1000;
		reqHeader.put("App-Key", chatAppInfo.getAppKey());
		reqHeader.put("Nonce", nonce);
		reqHeader.put("Timestamp", timestamp + "");
		reqHeader.put("Signature", StringUtil.sha(chatAppInfo.getAppSecret() + nonce + timestamp, "UTF-8"));
		return reqHeader;
	}

}
