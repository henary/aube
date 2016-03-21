package com.aube.jms.consume.listener;

/**
 * 普通的的一個java除理消息的類
 * @author henary
 *
 */
public class ConsumerListener {

	/**
	 * 
	 * @param message 是由Message進行了轉化之後的對象
	 */
	public void handleMessage(String message){
		System.out.println("默認的消息處理方法----------------"+message);
	}
	public void receiveMessage(String message){
		System.out.println("receiveMessage----------"+message);
	}
	
}
