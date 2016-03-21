package com.aube.untrans.impl;

import org.springframework.stereotype.Service;

import com.aube.Config;
import com.aube.untrans.InitDataService;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

@Service("initDataService")
public class InitDataServiceImpl implements InitDataService {
	private final transient AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);

	@Override
	public void startInit(String info, Runnable call) {
		dbLogger.warn("start init data:" + info);
		call.run();
		dbLogger.warn("end init data:" + info);
	}

	@Override
	public void delayInit(String info, Runnable call, long delayMills) {
		dbLogger.warn("start init data:" + info);
		call.run();
		dbLogger.warn("end init data:" + info);
	}
}
