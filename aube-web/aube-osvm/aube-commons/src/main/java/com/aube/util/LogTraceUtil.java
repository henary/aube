package com.aube.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 用来做系统出错定位跟踪
 */
public class LogTraceUtil {
	private static List<LogTrace> traceList = new ArrayList<LogTrace>();
	public static void addTrace(LogTrace trace){
		traceList.add(trace);
	}
	public static List<String> getTraceInfo(){
		List<String> result = new ArrayList<String>();
		for(LogTrace trace: traceList){
			try{
				String ts = trace.getTrace();
				if(StringUtils.isNotBlank(ts)){
					result.add(ts);
				}
			}catch(Throwable e){
			}
		}
		return result;
	}
	public static interface LogTrace{
		String getTrace();
	}
}
