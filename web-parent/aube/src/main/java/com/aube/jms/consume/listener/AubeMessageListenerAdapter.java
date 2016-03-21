package com.aube.jms.consume.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/**
 * aube的消息监听类
 * @author henary
 *
 * 如果为MessageListener 或者sessionAwareListenerContainer 时spring直接利用收到的message对象作为方法参数调用onMessage方法
 * 如果为普通的java类时，spring将利用message进行类型转化后的参数通过反射去调用真正的目标处理的方法
 * 
 */
public class AubeMessageListenerAdapter extends MessageListenerAdapter {

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		// TODO Auto-generated method stub
		super.onMessage(message, session);
	}
	
	

}
