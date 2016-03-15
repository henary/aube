package com.aube.untrans.monitor.impl;

import java.io.CharConversionException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.aube.Config;
import com.aube.support.AubeExecutorThreadFactory;
import com.aube.support.VelocityTemplate;
import com.aube.untrans.monitor.MonitorData;
import com.aube.untrans.monitor.MonitorService;
import com.aube.util.BeanUtil;
import com.aube.util.DateUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;

/**
 * 通过消息队列实现
 */
public abstract class AbstractMonitorService implements MonitorService {
	protected static String FLAG_ADMIN = "admin";
	protected Map<String, AtomicInteger> callcountMap = new HashMap<String, AtomicInteger>();
	protected AubeLogger dbLogger = LoggerUtils.getLogger(this.getClass(), Config.getServerIp(), Config.SYSTEMID);
	protected ThreadPoolExecutor executor;
	@Autowired
	@Qualifier("velocityTemplate")
	private VelocityTemplate velocityTemplate;

	@Override
	public Map<String, String> getMonitorStatus() {
		Map<String, String> result = new LinkedHashMap<String, String>();
		result.put("count", "" + executor.getCompletedTaskCount());
		result.put("queued", "" + executor.getTaskCount());
		return result;
	}

	@Override
	public void addAccessLog(Map<String, String> logEntry) {
		addMonitorEntry(MonitorData.DATATYPE_ACCESSLOG, logEntry);
	}

	protected void setupConsumerThread(int threadSize) {
		// FixedPoolSize
		executor = new ThreadPoolExecutor(threadSize, threadSize, 300, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(), new AubeExecutorThreadFactory(this.getClass().getSimpleName()));
		executor.allowCoreThreadTimeOut(false);
		dbLogger.warn("MonitorThread started!");
	}

	@Override
	public String logException(EXCEPTION_TAG tag, String location, String title, Throwable ex,
			Map<String, String> otherinfo) {
		Map<String, String> row = new HashMap<String, String>();
		String exctrace = null;
		if (ex != null) {
			String exceptionTrace = title + "\n";
			String exceptionName = ex.getClass().getSimpleName();
			if (ex instanceof MissingServletRequestParameterException || ex instanceof TypeMismatchException
					|| StringUtils.contains(ex.getClass().getName(), "ClientAbortException")) {
				exctrace = LoggerUtils.getExceptionTrace(ex, 10);
			} else if (StringUtils.contains(ex.getClass().getName(), "HibernateOptimisticLockingFailureException")) {
				exctrace = LoggerUtils.getExceptionTrace(ex, 50);
			} else {
				exctrace = LoggerUtils.getExceptionTrace(ex, 200);
			}
			exceptionTrace += exctrace;
			if (ex instanceof IllegalStateException && ex.getCause() instanceof CharConversionException) {
				// url编码错误，不做登记
				return exctrace;
			}
			row.put("exceptionName", exceptionName);
			row.put("exceptionTrace", exceptionTrace);
		} else {
			row.put("exceptionName", "无");
			row.put("exceptionTrace", title + "\n" + location + "\n" + otherinfo);
		}

		if (otherinfo != null) {
			row.putAll(otherinfo);
		}
		row.put("tag", "" + tag);
		row.put("title", title);
		row.put("location", location);
		row.put("server", Config.getServerIp());
		String curtime = DateUtil.getCurFullTimestampStr();
		row.put("addtime", curtime);
		row.put("adddate", curtime.substring(0, 10));

		addMonitorEntry(MonitorData.DATATYPE_LOGENTRY, row);

		if (otherinfo != null) {
			String remoteIp = otherinfo.get("remoteIp");

			String exceptionName = row.get("exceptionName");
			if (StringUtils.equals(exceptionName, "BindException")
					|| StringUtils.equals(exceptionName, "TypeMismatchException")
					|| StringUtils.equals(exceptionName, "NumberFormatException")) {
				String reqUri = otherinfo.get("reqUri");
				String reqParams = otherinfo.get("reqParams");
				Map<String, String> params = new HashMap<String, String>();
				params.put("reqParams", reqParams);
				params.put("exceptionType", "AttackException");
				params.put("exceptionName", exceptionName);
				this.logViolation(remoteIp, reqUri, params);
			}
		}
		return exctrace;
	}

	@Override
	public void logViolation(String ip, String resource, Map<String, String> params) {
		Map<String, String> row = new LinkedHashMap<String, String>();
		row.put("ip", ip);
		row.put("resource", resource);
		row.put("systemId", Config.SYSTEMID);
		row.put("accessTime", DateUtil.getCurFullTimestampStr());

		if (params != null) {
			row.putAll(BeanUtil.toSimpleStringMap(params));
		}
		addMonitorEntry(MonitorData.DATATYPE_VIOLATION, row);
	}

	@Override
	public void addMonitorEntry(String datatype, Map<String, String> entry) {
		// TODO
	}
}
