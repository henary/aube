package com.aube.web.support;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.aube.Config;
import com.aube.untrans.monitor.MonitorService;
import com.aube.untrans.monitor.MonitorService.EXCEPTION_TAG;
import com.aube.untrans.monitor.SysLogType;
import com.aube.util.BeanUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class AubeWebApplicationContext extends XmlWebApplicationContext{
	private final transient AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);
	private ThreadPoolExecutor executor;
	private MonitorService monitorService;
	private boolean init = false;
	
	public AubeWebApplicationContext(){
		executor = new ThreadPoolExecutor(2, 2, 300, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		executor.allowCoreThreadTimeOut(false);
	}
	@Override
	public void publishEvent(ApplicationEvent event) {
		if(event instanceof ServletRequestHandledEvent){
			RequestEventHolder.setEvent((ServletRequestHandledEvent) event);
			executor.execute(new LogTask((ServletRequestHandledEvent) event));
		}else{
			super.publishEvent(event);
		}
	}
	private class LogTask implements Runnable{
		private ServletRequestHandledEvent event;
		public LogTask(ServletRequestHandledEvent event){
			this.event = event;
		}
		@Override
		public void run() {
			String url = event.getRequestUrl();
			Map<String, Integer> info = ResourceStatsUtil.clearUriIfMaxClick(200);
			if(info!=null){//不存在次数超多
				init();
				if(monitorService != null){
					monitorService.logException(EXCEPTION_TAG.PAGE, "GewaDispatcherServlet.noHandlerFound", "不存在的Acition", null, BeanUtil.toSimpleStringMap(info));
				}
			}
			if(!event.wasFailure()){
				Map<String, String> logMap = ResourceStatsUtil.updateUriProcessTime(url, event.getProcessingTimeMillis(), 1000);
				if(logMap != null){
					log(logMap);
				}
			}
		}
	}
	private void init(){
		if(!init){
			try{
				init = true;
				monitorService = (MonitorService)getBean("monitorService", MonitorService.class);
			}catch(Exception e){
			}
		}
	}
	private void log(Map<String, String> logMap){
		init();
		if(monitorService!=null){
			// TODO 
		//	monitorService.addSysLog(SysLogType.reqStats, logMap);
		}else{
			dbLogger.warnMap(SysLogType.reqStats.name(), logMap);
		}
	}
}
