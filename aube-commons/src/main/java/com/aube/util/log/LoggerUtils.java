package com.aube.util.log;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aube.util.AubeIpConfig;

public class LoggerUtils {

	private static final String tracePackage = "com.aube";

	private static Map<String/* exception or method */, AtomicInteger> exceptionCount = new ConcurrentHashMap<String, AtomicInteger>();

	private static int singleMax = 0; // 最大异常次数
	private static AtomicInteger criticalCount = new AtomicInteger(); // 关键异常
	private static String singleMaxName = ""; // 最大异常名称

	public static String getSingleMaxName() {
		return singleMaxName;
	}

	public static int getSingleMax() {
		return singleMax;
	}

	private static Set<String> criticalException = new TreeSet<String>(Arrays.asList(
			"com.alibaba.dubbo.rpc.ProviderException", // 没有服务提供者
			"com.mongodb.MongoException", "java.lang.ArrayIndexOutOfBoundsException", "java.net.UnknownHostException",
			"org.springframework.dao.DataAccessResourceFailureException",
			"org.springframework.dao.RecoverableDataAccessException", "org.springframework.jdbc.BadSqlGrammarException",
			"org.springframework.jms.UncategorizedJmsException", "java.lang.StackOverflowError",
			"java.lang.NoClassDefFoundError"));

	
	public static <T> AubeLogger getLogger(Class<T> clazz){
		Logger logger = LoggerFactory.getLogger(clazz);
		return new JsonLogger(logger, AubeIpConfig.getServerip(), null);
	}
	public static AubeLogger getLogger(String name, String serverIp, String systemId) {
		Logger logger = LoggerFactory.getLogger(name);
		return new JsonLogger(logger, serverIp, systemId);
	}

	public static <T> AubeLogger getLogger(Class<T> clazz, String serverIp, String systemId) {
		Logger logger = LoggerFactory.getLogger(clazz);
		return new JsonLogger(logger, serverIp, systemId);
	}

	public static String getExceptionTrace(Throwable e) {
		return getExceptionTrace(e, 100);
	}

	public static String getExceptionTrace(Throwable e, int rows) {
		StringBuffer result = new StringBuffer(e.getClass().getCanonicalName() + ": " + e.getMessage());
		rows--;
		String tracedMethod = getExceptionTrace(result, e, rows);
		incrementCount(e, tracedMethod);
		return result.toString();
	}

	public static void incrementCount(Throwable e, String traceMethod) {
		if (e != null) {
			String name = e.getClass().getName();
			if (criticalException.contains(name)) {
				criticalCount.incrementAndGet();
			}
			if (StringUtils.isNotBlank(traceMethod)) {
				name += "@" + traceMethod;
			}
			AtomicInteger counter = exceptionCount.get(name);
			if (counter == null) {
				counter = new AtomicInteger(1);
				exceptionCount.put(name, counter);
			} else {
				int max = counter.incrementAndGet();
				if (max > singleMax) {
					singleMaxName = name;
					singleMax = max;
				}
			}
		}
	}

	private static String getExceptionTrace(StringBuffer result, Throwable e, int rows) {
		result.append(e);
		StackTraceElement[] traces = e.getStackTrace();
		String tmp = "", tracedMethod = null;
		for (int i = 0; i < traces.length && rows >= 0; i++) {
			tmp = traces[i].toString();
			if (tracedMethod == null && StringUtils.contains(tmp, tracePackage)) {
				tracedMethod = tmp;
			}
			result.append("\n\tat " + tmp);
			rows--;
		}
		if (rows > 0) {
			Throwable ourCause = e.getCause();
			if (ourCause != null) {
				result.append(ourCause);
				result.append("\nCaused by");
				rows--;
				if (rows > 0) {
					String trace = getExceptionTrace(result, ourCause, rows);
					if (tracedMethod == null) {
						tracedMethod = trace;
					}
				}
			}
		}
		return tracedMethod;
	}
}
