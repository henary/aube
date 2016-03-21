package aube.ssdb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration("/spring/appContext-ssdb.xml")  
public class SSDBTest {
	@Autowired
	@Qualifier("ssdb") 
	private SSDB ssdb;
	
	@Test
	public void setTest(){
		ssdb.set("hello","hello");
	}

}
