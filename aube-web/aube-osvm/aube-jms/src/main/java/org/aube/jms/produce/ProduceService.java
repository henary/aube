package org.aube.jms.produce;

import javax.jms.Destination;

/**
 *  消息发送者
 * @author henary
 *
 */
public interface ProduceService {

	  //sendmessage
	  public void sendMessage(Destination destination, final String message);
	  
}
