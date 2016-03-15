package com.aube.untrans;

import java.util.Map;

public interface JmsService {
	/**
	 * 增加一个发送队列
	 * @param queue
	 * @param persist
	 * @param override 已存在是否覆盖
	 * @return
	 */
	void addQueue(String queue, boolean persist, boolean override);
	/**
	 * @param msgtag
	 * @param keyList 用,分隔，多个key
	 * @param params 与keyList对应，多个值
	 */
	void sendMsgToDst(String dst, String tag, String msg);
	void sendMsgToDst(String dst, String tag, Map msg);
	void sendMsgToDst(String dst, String tag, String keyList, Object ... params);
	void sendMsgToDstWithMultiHead(String dst, String tag, Map msg, Map<String, String> headMap);
	
	/**
	 * 定时发送目标
	 * @param dst
	 * @param tag
	 * @param msgMap
	 * @param delay 首次延迟(ms)
	 */
	void delaySendMsgToDst(String dst, String tag, Map msgMap, long delay);
	/**
	 * 定时发送目标
	 * @param dst
	 * @param tag
	 * @param msg
	 * @param delay 首次延迟(ms)
	 */
	void delaySendMsgToDst(String dst, String tag, String msg, long delay);
	/**
	 * TODO:test
	 * 定时重复发送消息到dst
	 * @param dst
	 * @param tag
	 * @param msgMap
	 * @param delay 首次延迟(ms)
	 * @param period 每次间隔 (ms)
	 * @param repeatNum 重发次数(第一次发完后再重复repeatNum次）
	 */
	void periodSendMsgToDst(String dst, String tag, Map msgMap, long delay, long period, int repeatNum);
}
