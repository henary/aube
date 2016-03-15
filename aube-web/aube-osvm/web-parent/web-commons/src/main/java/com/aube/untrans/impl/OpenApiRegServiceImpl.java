package com.aube.untrans.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.Config;
import com.aube.support.lifecycle.BeforeUpdateService;
import com.aube.untrans.OpenApiRegService;
import com.aube.untrans.monitor.ZookeeperService;
import com.aube.util.HttpResult;
import com.aube.util.HttpUtils;
import com.aube.util.TimerHelper;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

/**
 */
public class OpenApiRegServiceImpl implements OpenApiRegService, BeforeUpdateService {
	private AubeLogger logger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);
	@Autowired@Qualifier("keeperService")
	private ZookeeperService keeperService;
	
	private Map<String, Set<String>> regData = new HashMap<String, Set<String>>();

	private String checkUri;
	public void setCheckUri(String checkUri) {
		this.checkUri = checkUri;
	}
	
	/**
	 * OPENAPI服务注册处理
	 */
	@Override
	public void registerOpenApi(final String path, final String data){
		TimerHelper.TIMER.schedule(new Runnable() {
			@Override
			public void run() {
				registerOpenApiInternal(path, data);
			}
		}, 5000);
				
	}
	private void registerOpenApiInternal(String path, String data){
		try{
			boolean openapiOk = checkUrl();
			if(openapiOk){
				if(!keeperService.exist(path)){
					keeperService.addPresistentNode(path, System.currentTimeMillis()+"");
				}
				logger.warn("zookeeper添加临时节点 ： " + path + " 节点内容为：" + data);
				keeperService.registerNode(path + "/s", data);
				Set<String> dataList = regData.get(path);
				if(dataList==null){
					dataList = new HashSet<>();
					regData.put(path, dataList);
				}
				dataList.add(data);
			}
		}catch(Exception ex){
			logger.error("", ex);
		}
	}
	@Override
	public void unregisterOpenApi(){
		//注销OPENAPI
		logger.warn("unregister to openapi~~");
		for(Map.Entry<String, Set<String>> reg: regData.entrySet()){
			for(String data: reg.getValue()){
				keeperService.unRegisterEphemeralNode(reg.getKey(), data);
			}
		}
	}
	
	@Override
	public void beforeUpdate() {
		unregisterOpenApi();
	}
	private boolean checkUrl() {
		String checkUrl = "http://" + Config.getServerIp() + ":2000" + checkUri;

		for(int i=0;i<10;i++){
			HttpResult result = HttpUtils.getUrlAsString(checkUrl);
			if(result.isSuccess()){
				return true;
			}else{
				try {
					logger.warn("testOpenapi2000 failure!!");
					Thread.sleep(3000l);
				} catch (InterruptedException e) {
				}
			}
		}
		return false;
	}
	
}
