package com.aube.untrans.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aube.constans.MongoData;
import com.aube.constant.ErrorCodeConstant;
import com.aube.constants.AubesnsConfigConstants;
import com.aube.json.AubeSnsConfig;
import com.aube.json.chat.ChatAppInfo;
import com.aube.service.BaseService;
import com.aube.support.ResultCode;
import com.aube.untrans.AubeSnsConfigService;
import com.aube.untrans.InitDataService;
import com.aube.util.GoogleCache;

@Service("aubeSnsConfigService")
public class AubeSnsConfigServiceImpl extends BaseService implements AubeSnsConfigService, InitializingBean {

	private GoogleCache<Integer, String> AUBE_SNS_CONFIG = new GoogleCache<>(1000);
	@Autowired
	@Qualifier("initDataService")
	private InitDataService initDataService;

	@Override
	public ResultCode<String> getAubeSnsConfigById(Integer configId) {
		String snsConfig = AUBE_SNS_CONFIG.getIfPresent(configId);
		if (StringUtils.isBlank(snsConfig)) {
			return ResultCode.<String> getFailure(ErrorCodeConstant.CODE_CHAT_CFG_DEFAULT_NOT_EXITS);
		}
		return ResultCode.<String> getSuccessReturn(snsConfig);
	}

	@Override
	public ResultCode<ChatAppInfo> getUsedChatAppinfo() {
		ResultCode<String> snsConfigCode = getAubeSnsConfigById(AubesnsConfigConstants.CFG_USED_CHAT_APP_SOURCE);
		if (!snsConfigCode.isSuccess()) {
			return ResultCode.<ChatAppInfo> getFailure(snsConfigCode.getErrcode());
		}
		ChatAppInfo appInfo = mongoService.getObjectById(ChatAppInfo.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, snsConfigCode.getRetval());
		if (appInfo == null) {
			return ResultCode.<ChatAppInfo> getFailure(ErrorCodeConstant.CODE_CHAT_APP_NOT_EXITS);
		}
		return ResultCode.<ChatAppInfo> getSuccessReturn(appInfo);
	}

	private void initAubesnsConfig() {
		List<AubeSnsConfig> snsConfigList = mongoService.getObjectList(AubeSnsConfig.class);
		for (AubeSnsConfig snsConfig : snsConfigList) {
			AUBE_SNS_CONFIG.put(Integer.valueOf(snsConfig.realId().toString()), snsConfig.getContent());
		}
	}

	@Override
	public void refreshCurrent(String newConfig) {
		initAubesnsConfig();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initDataService.startInit("AubeSnsConfigService.initAubesnsConfig", new Runnable() {
			@Override
			public void run() {
				initAubesnsConfig();
			}
		});
	}
}
