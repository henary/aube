package com.aube.untrans.monitor.impl;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aube.untrans.JmsService;
import com.aube.untrans.monitor.MonitorData;
import com.aube.untrans.monitor.MonitorEntry;
import com.aube.untrans.monitor.MonitorService;
/**
 * 通过消息队列实现
 */
public class MonitorServiceImpl extends AbstractMonitorService implements MonitorService, InitializingBean {
//	@Autowired@Qualifier("monitorJmsService")
//	private JmsService monitorJmsService;
	@Override
	public void addMonitorEntry(String datatype, Map<String, String> entry) {
		executor.execute(new WorkThread(new MapMonitorEntry(datatype, entry)));
	}
	@Override
	public void addMonitorEntry(MonitorEntry entry) {
		executor.execute(new WorkThread(entry));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//重置队列，防止配置文件缺失队列
		for(String queue: MonitorData.queueMap.values()){
			// TODO 
//			monitorJmsService.addQueue(queue, false, false);
		}
		setupConsumerThread(10);
	}
	
	
	

	public class WorkThread implements Runnable{
		private MonitorEntry entry;
		public WorkThread(MonitorEntry entry){
			this.entry = entry;
		}
		@Override
		public void run() {
			String queue = MonitorData.getQueue(entry.getDatatype());
			// TODO 
			/*if(entry.getHeadMap()!=null){
				monitorJmsService.sendMsgToDstWithMultiHead(queue, entry.getDatatype(), entry.getDataMap(), entry.getHeadMap());
			}else{
				monitorJmsService.sendMsgToDst(queue, entry.getDatatype(), entry.getDataMap());
			}*/
		}
	}

}
