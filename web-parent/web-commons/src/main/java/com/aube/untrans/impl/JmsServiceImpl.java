package com.aube.untrans.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.util.ObjectUtils;

import com.aube.Config;
import com.aube.untrans.JmsService;
import com.aube.util.BeanUtil;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;
import com.aube.web.support.DynamicStats.LogCounter;
import com.aube.web.support.ResourceStatsUtil;

public class JmsServiceImpl extends JmsTemplate implements JmsService, InitializingBean{
	private final transient AubeLogger dbLogger = LoggerUtils.getLogger(getClass(), Config.getServerIp(), Config.SYSTEMID);
	private Map<String, String> queueMap = new HashMap<String, String>();
	private Map<String, Destination> dstMap = new HashMap<String, Destination>();
	private List<Destination> persistList = new ArrayList<Destination>();
	public JmsServiceImpl(){
	}
	public JmsServiceImpl(Map<String, String> queueMap){
		this.queueMap = queueMap;
	}
	@Override
	public void sendMsgToDst(String dst, String msgtag, String msg) {
		Destination dest = dstMap.get(dst);
		if(dest==null) {
			dbLogger.error("queue error:" + dst + ", using default!");
			dest = getDefaultDestination();
		}
		sendStringMsg(dest, msgtag, msg, null);
	}
	@Override
	public void sendMsgToDst(String dst, String msgtag, Map msgMap) {
		Destination dest = dstMap.get(dst);
		if(dest==null) {
			dbLogger.error("queue error:" + dst + ", using default!");
			dest = getDefaultDestination();
		}
		sendMapMsg(dest, msgtag, msgMap, null);
	}
	@Override
	public void sendMsgToDst(String dst, String msgtag, String keyList, Object...params) {
		Destination dest = dstMap.get(dst);
		if(dest==null) {
			dbLogger.error("queue error:" + dst + ", using default!");
			dest = getDefaultDestination();
		}
		sendMsg(dest, msgtag, keyList, params);
	}
	@Override
	public void sendMsgToDstWithMultiHead(String queueName, final String msgtag, final Map msgMap, final Map<String, String> headMap){
		Destination dest = dstMap.get(queueName);
		if(dest==null) {
			dbLogger.error("queue error:" + queueName + ", using default!");
			dest = getDefaultDestination();
		}
		long cur = System.currentTimeMillis();
		LogCounter counter = ResourceStatsUtil.getJmsStats().beforeProcess(queueName, cur);
		try{
			send(dest, new MessageCreator(){
				@Override
				public Message createMessage(Session session) throws JMSException {
					MapMessage message = session.createMapMessage();
					message.setStringProperty("msgtag", msgtag);
					if(headMap!=null){
						for(Map.Entry<String, String> entry: headMap.entrySet()){
							if(StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())){
								message.setStringProperty(entry.getKey(), entry.getValue());
							}
						}
					}
					for (Object key : msgMap.keySet()) {
						if (!(key instanceof String)) {
							throw new MessageConversionException("Cannot convert non-String key of type [" +
									ObjectUtils.nullSafeClassName(key) + "] to JMS MapMessage entry");
						}
						message.setObject((String) key, msgMap.get(key));
					}
					return message;
				}
			});
		}catch (Exception e){
			dbLogger.warn(e, 20);
		}finally{
			ResourceStatsUtil.getJmsStats().afterProcess(counter, System.currentTimeMillis(), false);
		}

	}
	private void sendMsg(Destination dst, String msgtag, String keyList, Object...params) {
		if(StringUtils.isBlank(keyList) || params==null) return;
		Map msgMap = new HashMap();
		String[] keys = keyList.split("[, ]+");
		for(int i=0, size=Math.min(keys.length, params.length);i<size;i++){
			msgMap.put(keys[i], params[i]);
		}
		sendMapMsg(dst, msgtag, msgMap, null);
	}
	
	private void sendStringMsg(Destination dst, final String msgtag, final String msg, final Long delay) {
		long cur = System.currentTimeMillis();
		String name = "" + BeanUtil.get(dst, "qualifiedName");
		LogCounter counter = ResourceStatsUtil.getJmsStats().beforeProcess(name, cur);
		try{
			send(dst, new MessageCreator(){
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = session.createTextMessage(msg);
					if(delay!=null){//延迟发送
						message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
					}
					message.setStringProperty("msgtag", msgtag);
					return message;
				}
			});
		}catch (Exception e){
			dbLogger.warn(e, 20);
		}finally{
			ResourceStatsUtil.getJmsStats().afterProcess(counter, System.currentTimeMillis(), false);
		}
	}

	private void sendMapMsg(Destination dst, final String msgtag, final Map msgMap, final Long delay) {
		long cur = System.currentTimeMillis();
		String name = "" + BeanUtil.get(dst, "qualifiedName");
		LogCounter counter = ResourceStatsUtil.getJmsStats().beforeProcess(name, cur);
		try{
			send(dst, new MessageCreator(){
				@Override
				public Message createMessage(Session session) throws JMSException {
					MapMessage message = session.createMapMessage();
					if(delay!=null){//延迟发送
						message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
					}
					message.setStringProperty("msgtag", msgtag);
					for (Object key : msgMap.keySet()) {
						if (!(key instanceof String)) {
							throw new MessageConversionException("Cannot convert non-String key of type [" +
									ObjectUtils.nullSafeClassName(key) + "] to JMS MapMessage entry");
						}
						message.setObject((String) key, msgMap.get(key));
					}
					return message;
				}
			});
		}catch (Exception e){
			dbLogger.warn(e, 20);
		}finally{
			ResourceStatsUtil.getJmsStats().afterProcess(counter, System.currentTimeMillis(), false);
		}
	}
	public void setQueueMap(Map<String, String> queueMap) {
		this.queueMap = queueMap;
	}
	@Override
	protected void doSend(MessageProducer producer, Message message) throws JMSException {
		if (isExplicitQosEnabled()) {
			producer.send(message, getDeliveryMode(), getPriority(), getTimeToLive());
		}else {
			if(persistList.contains(producer.getDestination())) {
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			}else{
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			producer.send(message);
		}
	}
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		long cur = System.currentTimeMillis();
		for(String key: queueMap.keySet()){
			Destination dst = new ActiveMQQueue(key);
			dstMap.put(key, dst);
			if(StringUtils.equals(queueMap.get(key), "persist")){
				persistList.add(dst);
			}
			String name = "" + BeanUtil.get(dst, "qualifiedName");
			ResourceStatsUtil.getJmsStats().register(name, cur);
			dbLogger.warn("regester jms stats:" + name);
		}
		//性能
		this.setMessageIdEnabled(false);
		this.setMessageTimestampEnabled(false);
	}
	@Override
	public void addQueue(String queue, boolean persist, boolean override) {
		if(override || !dstMap.containsKey(queue)){
			Destination dst = new ActiveMQQueue(queue);
			dstMap.put(queue, dst);
			if(persist){
				persistList.add(dst);
			}
			String name = "" + BeanUtil.get(dst, "qualifiedName");
			long cur = System.currentTimeMillis();
			ResourceStatsUtil.getJmsStats().register(name, cur);
			dbLogger.warn("regester jms stats:" + name);
		}
		
	}
	@Override
	public void delaySendMsgToDst(String dst, String tag, Map msgMap, long delay) {
		Destination dest = dstMap.get(dst);
		if(dest==null) {
			dbLogger.error("queue error:" + dst + ", using default!");
			dest = getDefaultDestination();
		}
		sendMapMsg(dest, tag, msgMap, delay);
	}
	@Override
	public void periodSendMsgToDst(String dst, final String tag, final Map msgMap, final long delay, final long period, final int repeatNum){
		Destination dest = dstMap.get(dst);
		if(dest==null) {
			dbLogger.error("queue error:" + dst + ", using default!");
			dest = getDefaultDestination();
		}
		long cur = System.currentTimeMillis();
		String name = "" + BeanUtil.get(dest, "qualifiedName");
		LogCounter counter = ResourceStatsUtil.getJmsStats().beforeProcess(name, cur);
		try{
			send(dst, new MessageCreator(){
				@Override
				public Message createMessage(Session session) throws JMSException {
					MapMessage message = session.createMapMessage();
					//String scheduleId, 
					message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
					if(repeatNum>0){
						message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
						message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeatNum);
					}
					message.setStringProperty("msgtag", tag);
					for (Object key : msgMap.keySet()) {
						if (!(key instanceof String)) {
							throw new MessageConversionException("Cannot convert non-String key of type [" +
									ObjectUtils.nullSafeClassName(key) + "] to JMS MapMessage entry");
						}
						message.setObject((String) key, msgMap.get(key));
					}
					return message;
				}
			});
		}catch (Exception e){
			dbLogger.warn(e, 20);
		}finally{
			ResourceStatsUtil.getJmsStats().afterProcess(counter, System.currentTimeMillis(), false);
		}
	}

	@Override
	public void delaySendMsgToDst(String dst, String tag, String msg, long delay) {
		Destination dest = dstMap.get(dst);
		if(dest==null) {
			dbLogger.error("queue error:" + dst + ", using default!");
			dest = getDefaultDestination();
		}
		sendStringMsg(dest, tag, msg, delay);
	}
}
