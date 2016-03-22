package com.aube.web.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.Config;
import com.aube.untrans.monitor.ZookeeperService;
import com.aube.util.DateUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class AubeWebAppPostProcessor implements WebAppPostProcessor {
	private AubeLogger logger = LoggerUtils.getLogger(AubeWebAppPostProcessor.class, Config.getServerIp(),
			Config.SYSTEMID);
	@Autowired
	@Qualifier("keeperService")
	private ZookeeperService keeperService;

	@Override
	public void init() {
		// 1)服务节点注册
		processServerReg();
	}

	/**
	 * 服务注册处理
	 */
	private void processServerReg() {
		logger.warn("check init ok!");
		String serverPath = "/server/" + Config.SYSTEMID + "/";
		try {
			keeperService.registerNode(serverPath, Config.getServerIp() + "|" + Config.getHostname() + "|"
					+ DateUtil.formatTimestamp(System.currentTimeMillis()));
			logger.warn("register server on node:" + Config.getHostname() + "---->" + Config.getServerIp());
		} catch (Exception e) {
			logger.warn("", e);
		}
	}
}
