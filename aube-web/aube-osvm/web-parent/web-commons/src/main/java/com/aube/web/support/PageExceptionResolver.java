package com.aube.web.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.aube.Config;
import com.aube.untrans.monitor.MonitorService;
import com.aube.util.BaseWebUtils;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

public class PageExceptionResolver extends SimpleMappingExceptionResolver implements InitializingBean{
	private final transient AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);
	@Autowired(required=false)@Qualifier("monitorService")
	private MonitorService monitorService;
	private String sensitive;
	public void setSensitive(String sensitive) {
		this.sensitive = sensitive;
	}
	private String[] sensitiveList = new String[]{};

	protected Map saveExceptionMessage(Exception ex, HttpServletRequest request) {
		String uri = request.getRequestURI();
		String remoteIp = BaseWebUtils.getRemoteIp(request);
			String remoteIp2 = request.getHeader("aube-remote-ip");
			if(StringUtils.isNotBlank(remoteIp2)){
				remoteIp = remoteIp2;
			}
		
		String title = uri + "@" + Config.getServerIp() + ", RemoteIp:" + remoteIp;
		String reqMap = BaseWebUtils.getParamStr(request, true, sensitiveList);
		Map<String, String> result = new HashMap<String, String>();
		result.put("remoteIp", remoteIp);
		result.put("reqParams", ""+reqMap);
		result.put("reqHeader", BaseWebUtils.getHeaderStr(request));
		result.put("reqUri", uri);
		if(monitorService!=null){
			String excTrace = monitorService.logException(MonitorService.EXCEPTION_TAG.PAGE, uri, title, ex, result);
			dbLogger.error(result+excTrace);
		}else{
			dbLogger.error(result+"");
		}
		return result;
	}
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		saveExceptionMessage(ex, request);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isNotBlank(sensitive)){
			sensitiveList = StringUtils.split(sensitive, ",");
		}
	}
}
