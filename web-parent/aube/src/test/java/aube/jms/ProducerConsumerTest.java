package aube.jms;

import javax.jms.Destination;

import org.aube.jms.produce.ProduceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration("/spring/appContext-jms.xml")  
public class ProducerConsumerTest {
	@Autowired
	@Qualifier("produceService") 
	private ProduceService produceService;   
	@Autowired  
	@Qualifier("queueDestination")   
	private Destination destination;  
	@Autowired  
    @Qualifier("sessionAwareQueue")   
	private Destination sessionAwareQueue;   
	@Autowired  
    @Qualifier("adapterQueue")   
	private Destination adapterQueue;   

	@Test
	public void testSend(){
		produceService.sendMessage(destination, "你好，生产者！这是heanry发送的消息" );   
	}


	@Test
	public void testSessionAwareMessageListener(){  
		 produceService.sendMessage(sessionAwareQueue, "测试SessionAwareMessageListener");   
	}

	@Test
	public void testMessageListenerAdapter(){
		 produceService.sendMessage(adapterQueue, "测试MessageListenerAdapter");   
	}

}
