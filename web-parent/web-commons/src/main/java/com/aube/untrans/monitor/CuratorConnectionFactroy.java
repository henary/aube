package com.aube.untrans.monitor;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;

public interface CuratorConnectionFactroy {
	/**
	 * 获取初始化后的CuratorFramework
	 * @return
	 * @throws IOException 未初始化完成
	 */
	CuratorFramework getCuratorFramework() throws IOException ;
	
	void init() throws IOException;

	void destroy() throws InterruptedException;
}
