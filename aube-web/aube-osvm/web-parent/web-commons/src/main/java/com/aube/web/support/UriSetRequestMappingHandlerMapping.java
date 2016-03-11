package com.aube.web.support;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class UriSetRequestMappingHandlerMapping extends RequestMappingHandlerMapping{
	protected Set<String> uriSet = new HashSet<String>();
	@Override
	protected void handlerMethodsInitialized(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
		String contextPath = this.getServletContext().getContextPath();
		for(Map.Entry<RequestMappingInfo, HandlerMethod> info: handlerMethods.entrySet()){
			uriSet.addAll(info.getKey().getPatternsCondition().getPatterns());
		}
		ResourceStatsUtil.registerUri(new HashSet<String>(uriSet), contextPath);
		initUriMapping(contextPath, uriSet);
	}
	@Override
	protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
		if(uriSet.contains(lookupPath)){
			return super.lookupHandlerMethod(lookupPath, request);
		}else{
			return null;
		}
	}
	protected void initUriMapping(String contextPath, Set<String> uris){
	}
}
