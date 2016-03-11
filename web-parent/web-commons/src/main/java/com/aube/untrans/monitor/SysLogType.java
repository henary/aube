package com.aube.untrans.monitor;

public enum SysLogType {
	asynchTask("异步任务队列"), 
	userlogin("用户登录"),
	reqStats("Request请求统计日志"),
	SMSERR("短信发送失败"),
	monitor("监控数据"),
	smswarn("报警短信"),
	terminalLog("终端监控日志"),
	openapiLog("openapi监控日志"),
	userOp("用户重要操作"),
	saOp("SA动作");
	private String description;
	public String getDescription() {
		return description;
	}
	private SysLogType(String description){
		this.description = description;
	}
}
