package com.aube.untrans;

import com.aube.json.chat.ChatAppInfo;
import com.aube.support.ResultCode;
import com.aube.untrans.monitor.ConfigTrigger;

public interface AubeSnsConfigService extends ConfigTrigger {
	/**
	 * 根据ID获取配置信息
	 * 
	 * @param configId
	 * @return
	 */
	ResultCode<String> getAubeSnsConfigById(Integer configId);

	/**
	 * 正在使用的聊天应用服务
	 * 
	 * @return
	 */
	ResultCode<ChatAppInfo> getUsedChatAppinfo();

}
