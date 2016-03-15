package com.aube.untrans.monitor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.ServletRequestHandledEvent;

public interface RequestLogService {

	/**
	 * 记录日志信息
	 * @param uri
	 * @param params
	 * @param ip
	 */
	void logRequest(String ip, String uri, Map<String, String> params, HttpServletRequest req, ServletRequestHandledEvent event);
	/**
	 * @param uri
	 * @param referer
	 * @param xForwardedHost
	 * @param params
	 * @param isAjaxReq
	 * @param uvKey
	 * @param memberKey
	 */
	void logRequest(String ip, String uri, String referer, String userAgent, String xForwardedHost, Map<String, String> params, String isAjaxReq, String uvKey, String memberKey);
	
	/**
	 * 忽略的敏感关键参数
	 * @return
	 */
	void addSensitiveWords(String sensitive);
	
	/**
	 * 忽略的uri，如果uri后面带“*”号，则表示匹配前缀
	 * @return
	 */
	void addIgnoreUriList(List<String> ignoreUriList);


}
