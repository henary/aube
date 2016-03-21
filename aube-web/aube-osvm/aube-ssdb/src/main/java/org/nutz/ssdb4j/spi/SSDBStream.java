package org.nutz.ssdb4j.spi;

import java.io.Closeable;

/**
 * 封装与服务器通信的逻辑
 * @author henary
 *
 */
public interface SSDBStream extends Closeable {
	
	Response req(Cmd cmd, byte[] ...vals);
	
	void callback(SSDBStreamCallback callback);
}
