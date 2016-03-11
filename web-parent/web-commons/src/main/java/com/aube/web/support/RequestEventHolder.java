package com.aube.web.support;

import org.springframework.web.context.support.ServletRequestHandledEvent;

public class RequestEventHolder {
	private static boolean enable = false;
	private static ThreadLocal<ServletRequestHandledEvent> event = new ThreadLocal<ServletRequestHandledEvent>();
	public static ServletRequestHandledEvent getAndClearEvent(){
		ServletRequestHandledEvent srhe = event.get();
		event.set(null);
		return srhe;
	}
	public static void setEvent(ServletRequestHandledEvent evt){
		if(enable){
			event.set(evt);
		}
	}
	public static void setEnable(boolean enable2) {
		enable = enable2;
	}
}
