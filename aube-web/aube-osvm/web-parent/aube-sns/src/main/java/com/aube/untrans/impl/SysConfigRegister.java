package com.aube.untrans.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aube.Config;
import com.aube.constants.ConfigKey;
import com.aube.untrans.AubeSnsConfigService;
import com.aube.untrans.monitor.ConfigCenter;

@Service
public class SysConfigRegister implements InitializingBean {
	@Autowired
	@Qualifier("configCenter")
	private ConfigCenter configCenter;
	@Autowired
	@Qualifier("aubeSnsConfigService")
	private AubeSnsConfigService aubeSnsConfigService;

	@Override
	public void afterPropertiesSet() throws Exception {
		configCenter.register(Config.SYSTEMID, ConfigKey.KEY_LOCAL_SNSCONFIG, aubeSnsConfigService);
	}

}
