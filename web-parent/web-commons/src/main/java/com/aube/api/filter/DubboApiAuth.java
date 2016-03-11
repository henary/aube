package com.aube.api.filter;

public class DubboApiAuth {
	private String reqUri;		//请求URI
	private String systemid;	//系统ID
	private Long userid;	//登录用户ID
	private String username;		//登录用户名称
	private String sessionid;	//登录用户sessionid
	private String remoteIp;
	
	public DubboApiAuth(String reqUri, String systemid, Long userid, String username, String sessionid, String remoteIp){
		this.reqUri = reqUri;
		this.systemid = systemid;
		this.userid = userid;
		this.username = username;
		this.sessionid = sessionid;
		this.remoteIp = remoteIp;
	}
	
	
	public String getReqUri() {
		return reqUri;
	}
	
	public String getSystemid() {
		return systemid;
	}
	
	public Long getUserid() {
		return userid;
	}


	public String getUsername() {
		return username;
	}

	public String getSessionid() {
		return sessionid;
	}
	
	public String getRemoteIp() {
		return remoteIp;
	}
	
	
}
