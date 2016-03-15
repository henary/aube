package com.aube.untrans.monitor;

import java.util.Map;

public interface MonitorService {
	String KEY_TABLENAME = "TABLE_NAME";
	String KEY_HEXID = "HEXID";

	enum EXCEPTION_TAG{
		PAGE,		//页面异常
		SERVICE,	//服务异常
		JOB			//定时任务异常
	}
	/**
	 * 增加日志数据
	 * @param datatype
	 * @param entry
	 */
	void addMonitorEntry(String datatype, Map<String, String> entry);
	void addMonitorEntry(MonitorEntry entry);
	/**
	 * 获取统计信息，count：处理条数，queued：当前队列数
	 * @return
	 */
	Map<String, String> getMonitorStatus();
	
	/**
	 * 
	 * @param tag
	 * @param location：uri 或 service、job方法
	 * @param title 标题
	 * @param e excepion
	 */
	String logException(EXCEPTION_TAG tag, String location, String title, Throwable e, Map<String, String> otherinfo);
	
	/**
	 * 增加非法访问记录
	 * @param ip
	 * @param params
	 */
	void logViolation(String ip, String resource, Map<String, String> params);
	
	void addAccessLog(Map<String, String> logEntry);
}
