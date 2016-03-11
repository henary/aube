package com.aube.untrans.monitor.impl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import com.aube.untrans.monitor.KeeperWatcher;
import com.aube.util.StringUtil;

public abstract class DataChangeWatcher extends KeeperWatcher implements NodeCacheListener  {
	private String prevalue;
	private NodeCache nodeCache;
	public DataChangeWatcher(String path){
		this.path = path;
	}
	
	protected abstract void processChange(String oldvalue, String newvalue);
	
	@Override
	public void nodeChanged() throws Exception {
		String tmp = prevalue;
		prevalue = getData();
		processChange(tmp, prevalue);
	}

	@Override
	public KeeperWatcher init(CuratorFramework zc) {
		this.client = zc;
		this.nodeCache = new NodeCache(client, path);
		this.nodeCache.getListenable().addListener(this);
		try {
			this.nodeCache.start(true);
		} catch (Exception e) {
			dbLogger.warn(e, 100);
		}
		this.prevalue = getData();
		return this;
	}
	private String getData(){
		ChildData cur = nodeCache.getCurrentData();
		if(cur!=null){
			byte[] data = cur.getData();
			if(data!=null){
				return StringUtil.getString(data, "utf-8");
			}
		}
		return null;
	}
}
