package com.aube.untrans.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.aube.constans.MongoData;
import com.aube.constant.AubeErrorCodeConstants;
import com.aube.json.AubeConfig;
import com.aube.service.BaseService;
import com.aube.support.ResultCode;
import com.aube.untrans.AubeConfigService;


@Service("aubeConfigService")
public class AubeConfigServiceImpl extends BaseService implements AubeConfigService, InitializingBean {

	@Override
	public ResultCode<String> getAubeConfig(String id) {
		AubeConfig aubeConfig = mongoService.getObjectById(AubeConfig.class, MongoData.ID_NAME_SYSTEMID_NAME_SYSTEM, id);
		if (aubeConfig == null || StringUtils.isBlank(aubeConfig.getContent())) {
			return ResultCode.<String> getFailure(AubeErrorCodeConstants.CODE_NOT_FIND_CFG, "AubeConfig id:" + id);
		}
		return ResultCode.<String> getSuccessReturn(aubeConfig.getContent());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}

}
