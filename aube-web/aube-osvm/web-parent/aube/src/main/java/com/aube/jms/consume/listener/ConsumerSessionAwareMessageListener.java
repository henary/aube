package com.aube.jms.consume.listener;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

/**
 * 收到消息時還能返回消息
 * @author henary
 * 2016年3月21日
 *
 */
public class ConsumerSessionAwareMessageListener   implements  SessionAwareMessageListener<TextMessage> {
	private Destination destination;   

	
	public Destination getDestination(){
		return destination;
	}
	 
	public void setDestination(Destination destination) {   
		 this.destination = destination;   
    }

	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		System.out.println("收到一条消息");   
		System.out.println("消息内容是：" + message.getText());   
		MessageProducer producer = session.createProducer(destination);   
		Message textMessage = session.createTextMessage("ConsumerSessionAwareMessageListener。。。");   
		producer.send(textMessage);   		
	}   

	
 
}
