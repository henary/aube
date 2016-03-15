package com.aube.untrans.monitor.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;

import com.aube.untrans.monitor.ConfigCenter;
import com.aube.untrans.monitor.ConfigData;
import com.aube.untrans.monitor.ConfigTrigger;
import com.aube.untrans.monitor.ConfigWatcher;
import com.aube.untrans.monitor.ZookeeperService;

public class ConfigCenterImpl implements ConfigCenter {
	private static final String GLOBAL = "global";
	private Map<String, ConfigTrigger> triggerMap = new ConcurrentHashMap<String, ConfigTrigger>();
	
	private Map<String, Integer> partitionMap = new ConcurrentHashMap<String, Integer>();
	//记录当前写的是哪个partition
	private Map<String, AtomicInteger> curPartMap = new ConcurrentHashMap<String, AtomicInteger>();
	private Set<String/*systemid,tag*/> delayList = new HashSet<String>();
	@Autowired
	private ZookeeperService keeperService;
	@Override
	public void register(String systemid, String tag, ConfigTrigger trigger){
		String path = createPath(systemid, tag);
		triggerMap.put(systemid + "," + tag, trigger);
		keeperService.addMonitor(new ConfigWatcher(path, trigger));
		
		//有新注册，扫描之前未处理掉的delay项
		processDelay();
	}
	@Override
	public void registerGlobal(String tag, ConfigTrigger trigger) {
		register(GLOBAL, tag, trigger);
	}

	@Override
	public void register(String systemid, String tag, ConfigTrigger trigger, int partition) {
		//1)注册默认项
		register(systemid, tag, trigger);
		
		partition = Math.min(16, partition);
		if(partition>0){
			List<String> partList = createPartitionPath(systemid, tag, partition);
			String key = systemid + "," + tag;
			partitionMap.put(key, partition);
			for(String part: partList){
				keeperService.addMonitor(new ConfigWatcher(part, trigger));
			}
			curPartMap.put(key, new AtomicInteger(0));
		}
	}
	@Override
	public void registerGlobal(String tag, ConfigTrigger trigger, int partition) {
		register(GLOBAL, tag, trigger, partition);
	}
	@Override
	public void refresh(String systemid, String tag){
		refresh(systemid, tag, "" + new Timestamp(System.currentTimeMillis()));
	}
	@Override
	public void refresh(String systemid, String tag, String data) {
		String path = "/config/" + systemid + "/" + tag;
		String key = systemid + "," + tag;
		Integer partcount = partitionMap.get(key);
		if(partcount!=null){
			int curpart = curPartMap.get(key).getAndIncrement() % partcount;
			path = path + "/part" + curpart; 
		}
		
		if(!keeperService.exist(path)){
			keeperService.addPresistentNode(path, data);
		}else{
			keeperService.updateNode(path, data);
		}

	}
	private void processDelay(){
		if(!delayList.isEmpty()){
			Iterator<String> it = delayList.iterator();
			while(it.hasNext()){
				String s = it.next();
				if(triggerMap.containsKey(s)){
					it.remove();
					int idx = s.indexOf(',');
					String systemid = s.substring(0, idx);
					String tag = s.substring(idx +1);
					reloadCurrent(systemid, tag);
				}
			}
		}
	}
	private String createPath(String systemid, String tag){
		String path = "/config/" + systemid;
		if(!keeperService.exist(path)){
			keeperService.addPresistentNode(path, "config trigger for " + systemid);
		}
		path += "/" + tag;
		if(!keeperService.exist(path)){
			keeperService.addPresistentNode(path, "" + new Timestamp(System.currentTimeMillis()));
		}
		return path;
	}
	private List<String> createPartitionPath(String systemid, String tag, int partition){
		List<String> result = new ArrayList<String>();
		String path = "/config/" + systemid;
		if(!keeperService.exist(path)){
			keeperService.addPresistentNode(path, "config trigger for " + systemid);
		}
		path += "/" + tag;
		if(!keeperService.exist(path)){
			keeperService.addPresistentNode(path, "" + new Timestamp(System.currentTimeMillis()));
		}
		for(int i=0; i< partition; i++){
			String part = path + "/part" + i;
			if(!keeperService.exist(part)){
				keeperService.addPresistentNode(part, "");
			}
			result.add(part);
		}
		return result;
	}
	@Override
	public void reloadCurrent(String systemid, String tag) {
		String key = systemid + "," + tag;
		if(!triggerMap.containsKey(key)){
			delayList.add(systemid + "," + tag);
		}else{
			String path = createPath(systemid, tag);
			String data = keeperService.getNodeData(path);
			triggerMap.get(systemid + "," + tag).refreshCurrent(data);
		}
	}
	@Override
	public void refreshGlobal(String tag) {
		refresh(GLOBAL, tag);
	}
	@Override
	public void refreshGlobal(String tag, String data) {
		refresh(GLOBAL, tag, data);
	}
	@Override
	public void reloadGlobal(String tag) {
		reloadCurrent(GLOBAL, tag);
	}
	@Override
	public ConfigData getConfigData(String systemid, String tag) {
		ConfigTrigger cfg = triggerMap.get(systemid + "," + tag);
		if(cfg instanceof ConfigData){
			return (ConfigData) cfg;
		}
		return null;
	}

}
