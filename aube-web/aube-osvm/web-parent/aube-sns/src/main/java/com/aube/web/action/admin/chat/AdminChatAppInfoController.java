package com.aube.web.action.admin.chat;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aube.Config;
import com.aube.constans.MongoData;
import com.aube.constants.AubesnsConfigConstants;
import com.aube.constants.ChatConstants;
import com.aube.constants.ConfigKey;
import com.aube.json.AubeSnsConfig;
import com.aube.json.chat.ChatAppInfo;
import com.aube.untrans.monitor.ConfigCenter;
import com.aube.util.JsonUtils;
import com.aube.web.action.AubeSnsBaseController;

@Controller
public class AdminChatAppInfoController extends AubeSnsBaseController {
	@Autowired
	@Qualifier("configCenter")
	private ConfigCenter configCenter;
	@RequestMapping("/admin/chat/defaultApp.xhtml")
	@ResponseBody
	public String setDefaultChatApp(String sources) {
		AubeSnsConfig snsConfig = null;
		if (StringUtils.isNotBlank(sources)) {
			 snsConfig = new AubeSnsConfig(AubesnsConfigConstants.CFG_USED_CHAT_APP_SOURCE, sources);
			mongoService.saveOrUpdateObject(snsConfig, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
			configCenter.refresh(Config.SYSTEMID, ConfigKey.KEY_LOCAL_SNSCONFIG);
		} else {
			snsConfig = mongoService.getObjectById(AubeSnsConfig.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, sources);
		}
		if (snsConfig == null) {
			return "请先配置admin/chat/setAppinfo.xhtml应用信息";
		}
		ChatAppInfo appInfo = mongoService.getObjectById(ChatAppInfo.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, snsConfig.getContent());
		configCenter.refresh(Config.SYSTEMID, ConfigKey.KEY_LOCAL_SNSCONFIG);
		return JsonUtils.writeGsonWithPretty(appInfo, true);
	}
	@RequestMapping("/admin/chat/setAppinfo.xhtml")
	@ResponseBody
	public String setAppinfo(String appKey, String appSecret, String appSource, Integer maxRunners) {
		ChatAppInfo appInfo = new ChatAppInfo(appSource, appKey, appSecret);
		appInfo.setMaxRunners(maxRunners == null ? ChatConstants.DEFAULT_GROUP_MAX_RUNNERS : maxRunners);
		mongoService.saveOrUpdateObject(appInfo, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM);
		return JsonUtils.writeGsonWithPretty(appInfo, true);
	}

	@RequestMapping("/admin/chat/appinfoList.xhtml")
	@ResponseBody
	public String chatAppInfoList() {
		List<ChatAppInfo> appInfoList = mongoService.getObjectList(ChatAppInfo.class);
		return JsonUtils.writeGsonWithPretty(appInfoList, true);
	}
}
