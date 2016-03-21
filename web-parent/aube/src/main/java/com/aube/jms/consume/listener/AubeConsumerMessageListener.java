package com.aube.jms.consume.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * jms消费者收到消息后的处理，后期可以自己改
 * 
 * @author henary
 * @version 1.0.0 2016年3月18日
 */
public class AubeConsumerMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		// TODO 测试用后期改好了

		try {
			TextMessage textMsg = (TextMessage) message;
			System.out.println("接收到一个纯文本消息。");
			System.out.println("消息内容是：" + textMsg.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
