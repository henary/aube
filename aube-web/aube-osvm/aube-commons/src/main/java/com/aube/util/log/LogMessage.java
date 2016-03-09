package com.aube.util.log;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.aube.util.JsonUtils;

public class LogMessage implements Serializable {

	private static final long serialVersionUID = -1449397931879372657L;
	private Map<String, String> data = new LinkedHashMap<String, String>();
	public LogMessage(String type, String server, String systemId, Map<String, String> paramsdata) {
		init(type, server, systemId);
		data.put("message", paramsdata.toString());
	}
	public LogMessage(String type, String server, String systemId, String message) {
		init(type, server, systemId);
		data.put("message", message);
	}
	public LogMessage(String type, String server, String systemId, String message, Throwable t) {
		init(type, server, systemId);
		data.put("message", message);
		data.put("exception", t.getClass().getCanonicalName());
		data.put("exceptionTrace", LoggerUtils.getExceptionTrace(t, 200));
	}
	private void init(String type, String server, String systemId){
		if(type!=null){
			data.put("type", type);
		}
		data.put("server", server);
		data.put("systemid", systemId);
	}

	public String getDataStr() {
		return JsonUtils.writeObjectToJson(this.data);
	}
	public Map<String, String> getDataMap() {
		return this.data;
	}

}
