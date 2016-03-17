package com.aube.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.aube.Config;

public class LoginUtils {

	public static final String SESS_COOKIE_NAME = Config.SESSION_COOKIE_NAME;
	public static final String COOKIE_NAME_TRACE = "_aubetc_";

	public static String setTraceIfNotExists(HttpServletRequest request, HttpServletResponse response) {
		String traceUkey = BaseWebUtils.getCookieValue(request, LoginUtils.COOKIE_NAME_TRACE);
		if (StringUtils.isBlank(traceUkey)) {
			traceUkey = setLogonTrace(response);
		}
		return traceUkey;
	}
	
	public static String getTrace(HttpServletRequest request) {
		String traceUkey = BaseWebUtils.getCookieValue(request, LoginUtils.COOKIE_NAME_TRACE);
		return traceUkey;
	}

	private static String setLogonTrace(HttpServletResponse response) {
		String traceUkey = LoginUtils.getTraceUkey();
		Cookie cookie = new Cookie(LoginUtils.COOKIE_NAME_TRACE, traceUkey);
		int duration = 60 * 60 * 24 * 30;// one month
		cookie.setMaxAge(duration);
		cookie.setPath("/");
		response.addCookie(cookie);
		return traceUkey;
	}

	private static String getTraceUkey() {
		String t = System.currentTimeMillis() + "";
		String r = "_" + StringUtil.getRandomString(4);
		String v = StringUtil.md5WithKey(t, 4);
		return t + r + "_" + v;
	}
}
