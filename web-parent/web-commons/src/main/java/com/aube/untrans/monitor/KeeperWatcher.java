package com.aube.untrans.monitor;

import org.apache.curator.framework.CuratorFramework;

import com.aube.Config;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public abstract class KeeperWatcher {
	protected final transient AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(),
			Config.SYSTEMID);
	protected String path;
	protected CuratorFramework client;

	/**
	 * 返回监听路径
	 * 
	 * @return
	 */
	public String getPath() {
		return this.path;
	}

	public abstract KeeperWatcher init(CuratorFramework zc);
}
